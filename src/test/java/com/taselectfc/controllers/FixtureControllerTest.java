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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taselectfc.Application;
import com.taselectfc.config.TestContext;
import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.model.Fixture;
import com.taselectfc.model.FixtureBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestContext.class, Application.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class FixtureControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FixtureDAO fixtureDAO;

    private Fixture fixture1;
    private Fixture fixture2;

    @Before
    public void setup() {
        Mockito.reset(fixtureDAO);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ZonedDateTime kickOff = ZonedDateTime.of(LocalDate.of(2015, Month.OCTOBER, 21), LocalTime.of(15, 00),
                ZoneId.of("GMT"));

        fixture1 = new FixtureBuilder().venue("Firhill").homeTeamName("Scotland").awayTeamName("Germany")
                .homeTeamFlag("Scotland.jpg").awayTeamFlag("Germany.jpg").date(kickOff).build();
        fixture2 = new FixtureBuilder().venue("Hampden").homeTeamName("Poland").awayTeamName("Scotland")
                .homeTeamFlag("Poland.jpg").awayTeamFlag("Scotland.jpg").date(kickOff).build();
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
    public void shouldGetOkResponseEvenIfFixtureListIsNull() throws Exception {
        when(fixtureDAO.findAll()).thenReturn(null);

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
        Fixture newFixture = new FixtureBuilder().id("ABC123").homeTeamName("Scotland").awayTeamName("Germany").build();
        when(fixtureDAO.save(newFixture)).thenReturn(fixture1);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    @Test
    public void shouldGetConflictWhenPostingFixtureWithIdThatAlreadyExists() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();
        when(fixtureDAO.exists("1234")).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldSaveFixtureWhenPuttingAFixture() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();

        when(fixtureDAO.save(newFixture)).thenReturn(fixture1);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc
                .perform(put("/fixtures/1234").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    @Test
    public void shouldUseIdInURLIfItDoesNotMatchIdInContent() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();
        String newFixtureJson = new ObjectMapper().writeValueAsString(newFixture);

        newFixture.setId("5678");
        when(fixtureDAO.save(newFixture)).thenReturn(newFixture);

        ResultActions result = mockMvc
                .perform(put("/fixtures/5678").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
    }

    @Test
    public void shouldUseIdFromURIIfContentDoesNotSpecifyId() throws Exception {
        Fixture newFixture = new FixtureBuilder().homeTeamName("Scotland").build();
        String newFixtureJson = new ObjectMapper().writeValueAsString(newFixture);

        newFixture.setId("1234");
        when(fixtureDAO.save(newFixture)).thenReturn(newFixture);

        ResultActions result = mockMvc
                .perform(put("/fixtures/1234").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
    }

    private void assertJsonContent(ResultActions result, Fixture... expectedFixtures) throws Exception {
        String fixturePath = "";
        if (expectedFixtures.length > 1) {
            fixturePath = ".[%s]";
        }

        for (int i = 0; i < expectedFixtures.length; i++) {
            Fixture fixture = expectedFixtures[i];
            String currentFixturePath = String.format(fixturePath, i);
            result.andExpect(jsonPath("$" + currentFixturePath + ".id", is(fixture.getId())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".venue", is(fixture.getVenue())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".homeTeamName", is(fixture.getHomeTeamName())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".awayTeamName", is(fixture.getAwayTeamName())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".homeTeamFlag", is(fixture.getHomeTeamFlag())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".awayTeamFlag", is(fixture.getAwayTeamFlag())));

            if (fixture.getKickoff() != null) {
                result.andExpect(jsonPath("$" + currentFixturePath + ".kickoff",
                        is(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(fixture.getKickoff()))));
            }
        }
    }

}
