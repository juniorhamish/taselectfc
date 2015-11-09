package com.taselectfc.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.IdGenerator;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.exception.DuplicateFixtureException;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;
import com.taselectfc.model.FixtureBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:TASelectFC-servlet.xml", "classpath:TestContext.xml" })
@WebAppConfiguration
public class FixtureControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FixtureDAO fixtureDAO;

    @Autowired
    private IdGenerator idGenerator;

    private Fixture fixture1;
    private Fixture fixture2;

    @Before
    public void setup() {
        Mockito.reset(fixtureDAO);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fixture1 = new FixtureBuilder().venue("Firhill").homeTeamName("Scotland").awayTeamName("Germany")
                .homeTeamFlag("Scotland.jpg").awayTeamFlag("Germany.jpg").date(new Date()).build();
        fixture2 = new FixtureBuilder().venue("Hampden").homeTeamName("Poland").awayTeamName("Scotland")
                .homeTeamFlag("Poland.jpg").awayTeamFlag("Scotland.jpg").date(new Date()).build();
    }

    @Test
    public void shouldGetAllFixturesFromTheDAOAsJSON() throws Exception {
        when(fixtureDAO.getAllFixtures()).thenReturn(Arrays.asList(fixture1, fixture2));

        ResultActions result = mockMvc.perform(get("/fixtures")).andExpect(status().isOk());

        assertJsonContent(result, fixture1, fixture2);
    }

    @Test
    public void shouldGetOkResponseEvenIfNoFixturesExist() throws Exception {
        when(fixtureDAO.getAllFixtures()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/fixtures")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetOkResponseEvenIfFixtureListIsNull() throws Exception {
        when(fixtureDAO.getAllFixtures()).thenReturn(null);

        mockMvc.perform(get("/fixtures")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetFixtureByIdFromDAOAsJSON() throws Exception {
        when(fixtureDAO.getFixtureById("1234")).thenReturn(fixture1);

        ResultActions result = mockMvc.perform(get("/fixtures/1234")).andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    @Test
    public void shouldGet404AndNoContentOnGetIfFixtureDoesNotExist() throws Exception {
        doThrow(FixtureNotFoundException.class).when(fixtureDAO).getFixtureById("1234");

        mockMvc.perform(get("/fixtures/1234")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldUseDAOToDeleteFixtureByIdAndGetFixtureInResponse() throws Exception {
        when(fixtureDAO.deleteFixtureById("1234")).thenReturn(fixture1);

        ResultActions result = mockMvc.perform(delete("/fixtures/1234")).andExpect(status().isOk());

        assertJsonContent(result, fixture1);
        verify(fixtureDAO, only()).deleteFixtureById("1234");
    }

    @Test
    public void shouldGet404AndNoContentOnDeleteIfFixtureDoesNotExist() throws Exception {
        doThrow(FixtureNotFoundException.class).when(fixtureDAO).deleteFixtureById("1234");

        mockMvc.perform(delete("/fixtures/1234")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldSaveFixtureOnPostAndGetJsonBack() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("ABC123").homeTeamName("Scotland").awayTeamName("Germany").build();

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
    }

    @Test
    public void shouldAssignNewIdToFixtureIfNotProvidedInRequest() throws Exception {
        Fixture newFixture = new FixtureBuilder().homeTeamName("Scotland").build();

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        UUID fixtureId = UUID.randomUUID();
        newFixture.setId(fixtureId.toString());

        when(idGenerator.generateId()).thenReturn(fixtureId);

        ResultActions result = mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
    }

    @Test
    public void shouldGetConflictWhenPostingFixtureWithIdThatAlreadyExists() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();
        doThrow(DuplicateFixtureException.class).when(fixtureDAO).create(newFixture);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        mockMvc.perform(post("/fixtures").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateNewFixtureWhenPuttingAFixtureThatDoesNotExist() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();

        when(fixtureDAO.exists("1234")).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc
                .perform(put("/fixtures/1234").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
        verify(fixtureDAO).create(newFixture);
    }

    @Test
    public void shouldSaveFixtureWhenPuttingAFixtureThatAlreadyExists() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();

        when(fixtureDAO.exists("1234")).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc
                .perform(put("/fixtures/1234").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
        verify(fixtureDAO).save(newFixture);
    }

    @Test
    public void shouldGetBadRequestIfIdInURLDoesNotMatchIdInContent() throws Exception {
        Fixture newFixture = new FixtureBuilder().id("1234").homeTeamName("Scotland").build();

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        mockMvc.perform(put("/fixtures/5678").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUseIdFromURIIfContentDoesNotSpecifyId() throws Exception {
        Fixture newFixture = new FixtureBuilder().homeTeamName("Scotland").build();
        String newFixtureJson = new ObjectMapper().writeValueAsString(newFixture);

        when(fixtureDAO.exists("1234")).thenReturn(false);
        newFixture.setId("1234");

        ResultActions result = mockMvc
                .perform(put("/fixtures/1234").contentType(APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, newFixture);
        verify(fixtureDAO).create(newFixture);
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

            if (fixture.getDate() != null) {
                result.andExpect(jsonPath("$" + currentFixturePath + ".date", is(fixture.getDate().getTime())));
            }
        }
    }

}
