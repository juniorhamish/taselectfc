package com.taselectfc.controllers;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.taselectfc.Application;
import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.dao.TeamDAO;
import com.taselectfc.model.Fixture;
import com.taselectfc.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class FixtureControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FixtureDAO fixtureDAO;

    @Autowired
    private TeamDAO teamDAO;

    private Fixture fixture1;
    private Fixture fixture2;
    private Team scotland;
    private Team germany;
    private Team poland;

    @Before
    public void setup() {
        fixtureDAO.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ZonedDateTime kickOff = ZonedDateTime.of(LocalDate.of(2015, Month.OCTOBER, 21), LocalTime.of(15, 00),
                ZoneId.of("GMT"));

        scotland = new Team.Builder().name("Scotland").flagName("Scotland.jpg").build();
        germany = new Team.Builder().name("Germany").flagName("Germany.jpg").build();
        poland = new Team.Builder().name("Poland").flagName("Poland.jpg").build();
        teamDAO.save(Arrays.asList(scotland, germany, poland));

        fixture1 = new Fixture.Builder().homeTeam(scotland).awayTeam(germany).kickoff(kickOff).build();
        fixture2 = new Fixture.Builder().homeTeam(poland).awayTeam(scotland).kickoff(kickOff).venue("Hampden").build();
    }

    @Test
    public void shouldGetAllFixturesFromTheDAOAsJSON() throws Exception {
        fixtureDAO.save(Arrays.asList(fixture1, fixture2));

        ResultActions result = mockMvc.perform(get("/fixtures")).andExpect(status().isOk());

        assertJsonContent(result, fixture1, fixture2);
    }

    @Test
    public void shouldGetOkResponseEvenIfNoFixturesExist() throws Exception {
        mockMvc.perform(get("/fixtures")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetFixtureByIdFromDAOAsJSON() throws Exception {
        fixtureDAO.save(fixture2);
        ResultActions result = mockMvc.perform(get("/fixtures/" + fixture2.getId())).andExpect(status().isOk());

        assertJsonContent(result, fixture2);
    }

    @Test
    public void shouldGet404AndNoContentOnGetIfFixtureDoesNotExist() throws Exception {
        mockMvc.perform(get("/fixtures/1234")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldUseDAOToDeleteFixtureByIdAndGetFixtureInResponse() throws Exception {
        fixtureDAO.save(fixture1);
        ResultActions result = mockMvc.perform(delete("/fixtures/" + fixture1.getId())).andExpect(status().isOk());

        assertJsonContent(result, fixture1);

        assertThat(fixtureDAO.findOne(fixture1.getId()), is(nullValue()));
    }

    @Test
    public void shouldGet404AndNoContentOnDeleteIfFixtureDoesNotExist() throws Exception {
        mockMvc.perform(delete("/fixtures/5678")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldSaveFixtureOnPostAndGetJsonBack() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String newFixtureJson = mapper.writeValueAsString(fixture1);

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
        if (fixture.getId() != null) {
            result.andExpect(jsonPath(jsonPath + ".id", is(fixture.getId().intValue())));
        }
        result.andExpect(jsonPath(jsonPath + ".venue", is(fixture.getVenue())));

        if (fixture.getKickoff() != null) {
            result.andExpect(jsonPath(jsonPath + ".kickoff",
                    is(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(fixture.getKickoff()))));
        }
    }

    private void assertTeamJson(ResultActions result, String teamJsonPath, Team team) throws Exception {
        if (team != null) {
            result.andExpect(jsonPath(teamJsonPath + ".id", is(team.getId().intValue())))
                    .andExpect(jsonPath(teamJsonPath + ".name", is(team.getName())))
                    .andExpect(jsonPath(teamJsonPath + ".flagName", is(team.getFlagName())));
        }
    }

}
