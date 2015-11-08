package com.taselectfc.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taselectfc.dao.FixtureDAO;
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
    public void shouldGetFixtureByIdFromDAOAsJSON() throws Exception {
        when(fixtureDAO.getFixtureById("1234")).thenReturn(fixture1);

        ResultActions result = mockMvc.perform(get("/fixtures/1234")).andExpect(status().isOk());

        assertJsonContent(result, fixture1);
    }

    @Test
    public void shouldGet404AndNoContentOnGetIfFixtureDoesNotExist() throws Exception {
        when(fixtureDAO.getFixtureById("1234")).thenReturn(null);

        mockMvc.perform(get("/fixtures/1234")).andExpect(status().is(404)).andExpect(content().string(""));
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
        when(fixtureDAO.deleteFixtureById("1234")).thenReturn(null);

        mockMvc.perform(delete("/fixtures/1234")).andExpect(status().is(404)).andExpect(content().string(""));
    }

    @Test
    public void shouldSaveFixtureOnPostAndGetJsonBack() throws Exception {
        Fixture newFixture = new FixtureBuilder().homeTeamName("Scotland").awayTeamName("Germany").build();
        when(fixtureDAO.save(newFixture)).thenReturn(fixture1);

        ObjectMapper mapper = new ObjectMapper();
        String newFixtureJson = mapper.writeValueAsString(newFixture);

        ResultActions result = mockMvc
                .perform(post("/fixtures").contentType(MediaType.APPLICATION_JSON).content(newFixtureJson))
                .andExpect(status().isOk());

        assertJsonContent(result, fixture1);
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
                    .andExpect(jsonPath("$" + currentFixturePath + ".awayTeamFlag", is(fixture.getAwayTeamFlag())))
                    .andExpect(jsonPath("$" + currentFixturePath + ".date", is(fixture.getDate().getTime())));
        }
    }

}
