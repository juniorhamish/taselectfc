package com.taselectfc.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taselectfc.Application;
import com.taselectfc.config.MockDAOTestContext;
import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.model.Fixture;
import com.taselectfc.model.FixtureBuilder;
import com.taselectfc.model.Team;

@ActiveProfiles("mock-dao")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MockDAOTestContext.class, Application.class })
@WebIntegrationTest(randomPort = true)
public class FixtureControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FixtureDAO fixtureDAO;

    private Fixture fixture1;
    private Fixture fixture2;
    private Team scotland;
    private Team germany;
    private Team poland;

    @Before
    public void setup() {
        Mockito.reset(fixtureDAO);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ZonedDateTime kickOff = ZonedDateTime.of(LocalDate.of(2015, Month.OCTOBER, 21), LocalTime.of(15, 00),
                ZoneId.of("GMT"));

        scotland = new Team();
        scotland.setName("Scotland");
        scotland.setFlagName("Scotland.jpg");

        germany = new Team();
        germany.setName("Germany");
        germany.setFlagName("Germany.jpg");

        poland = new Team();
        poland.setName("Poland");
        poland.setFlagName("Poland.jpg");

        fixture1 = new FixtureBuilder().venue("Firhill").homeTeam(scotland).awayTeam(germany).date(kickOff).build();
        fixture2 = new FixtureBuilder().venue("Hampden").homeTeam(poland).awayTeam(scotland).date(kickOff).build();
    }

    @Test
    public void shouldGetAllFixturesFromTheDAOAsJSON() throws Exception {
        when(fixtureDAO.findAll()).thenReturn(Arrays.asList(fixture1, fixture2));

        ResultActions result = mockMvc.perform(get("/fixtures")).andExpect(status().isOk());

        assertJsonContent(result, fixture1, fixture2);
    }

    @Test
    public void shouldGetOkResponseEvenIfNoFixturesExist() throws Exception {
        when(fixtureDAO.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/fixtures")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetFixtureByIdFromDAOAsJSON() throws Exception {
        when(fixtureDAO.findOne("1234")).thenReturn(fixture1);

        ResultActions result = mockMvc.perform(get("/fixtures/1234")).andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    @Test
    public void shouldGet404AndNoContentOnGetIfFixtureDoesNotExist() throws Exception {
        when(fixtureDAO.findOne("1234")).thenReturn(null);

        mockMvc.perform(get("/fixtures/1234")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldUseDAOToDeleteFixtureByIdAndGetFixtureInResponse() throws Exception {
        when(fixtureDAO.findOne("1234")).thenReturn(fixture1);

        ResultActions result = mockMvc.perform(delete("/fixtures/1234")).andExpect(status().isOk());

        assertJsonContent(result, fixture1);
        verify(fixtureDAO, times(1)).delete(fixture1);
    }

    @Test
    public void shouldGet404AndNoContentOnDeleteIfFixtureDoesNotExist() throws Exception {
        when(fixtureDAO.findOne("1234")).thenReturn(null);

        mockMvc.perform(delete("/fixtures/1234")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldSaveFixtureOnPostAndGetJsonBack() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("ABC123").homeTeam(scotland).awayTeam(germany).build();
        when(fixtureDAO.save(newFixture)).thenReturn(fixture1);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    private void assertJsonContent(ResultActions result, Fixture... expectedFixtures) throws Exception {
        String fixturePath = "$";
        if (expectedFixtures.length > 1) {
            fixturePath += ".[%s]";
        }

        for (int i = 0; i < expectedFixtures.length; i++) {
            Fixture fixture = expectedFixtures[i];
            String currentFixturePath = String.format(fixturePath, i);

            assertFixtureJson(result, currentFixturePath, fixture);
            assertTeamJson(result, currentFixturePath + ".homeTeam", fixture.getHomeTeam());
            assertTeamJson(result, currentFixturePath + ".awayTeam", fixture.getAwayTeam());

        }
    }

    private void assertFixtureJson(ResultActions result, String jsonPath, Fixture fixture) throws Exception {
        result.andExpect(jsonPath(jsonPath + ".id", is(fixture.getId())))
                .andExpect(jsonPath(jsonPath + ".venue", is(fixture.getVenue())));

        if (fixture.getKickoff() != null) {
            result.andExpect(jsonPath(jsonPath + ".kickoff",
                    is(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(fixture.getKickoff()))));
        }
    }

    private void assertTeamJson(ResultActions result, String teamJsonPath, Team team) throws Exception {
        if (team != null) {
            result.andExpect(jsonPath(teamJsonPath + ".id", is(team.getId())))
                    .andExpect(jsonPath(teamJsonPath + ".name", is(team.getName())))
                    .andExpect(jsonPath(teamJsonPath + ".flagName", is(team.getFlagName())));
        }
    }

}
