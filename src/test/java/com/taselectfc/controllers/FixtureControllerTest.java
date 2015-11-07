package com.taselectfc.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    private Date date;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        date = new Date();
        fixture1 = new FixtureBuilder().id("1234").venue("Firhill").homeTeamName("Scotland").awayTeamName("Germany")
                .homeTeamFlag("Scotland.jpg").awayTeamFlag("Germany.jpg").date(date).build();
        fixture2 = new FixtureBuilder().id("5678").venue("Hampden").homeTeamName("Poland").awayTeamName("Scotland")
                .homeTeamFlag("Poland.jpg").awayTeamFlag("Scotland.jpg").date(date).build();
    }

    @Test
    public void shouldGetAllFixturesFromTheDAOAsJSON() throws Exception {
        when(fixtureDAO.getAllFixtures()).thenReturn(Arrays.asList(fixture1, fixture2));

        mockMvc.perform(get("/fixtures")).andExpect(status().isOk()).andExpect(jsonPath("$.[0].id", is("1234")))
                .andExpect(jsonPath("$.[0].venue", is("Firhill")))
                .andExpect(jsonPath("$.[0].homeTeamName", is("Scotland")))
                .andExpect(jsonPath("$.[0].awayTeamName", is("Germany")))
                .andExpect(jsonPath("$.[0].homeTeamFlag", is("Scotland.jpg")))
                .andExpect(jsonPath("$.[0].awayTeamFlag", is("Germany.jpg")))
                .andExpect(jsonPath("$.[0].date", is(date.getTime()))).andExpect(jsonPath("$.[1].id", is("5678")))
                .andExpect(jsonPath("$.[1].venue", is("Hampden")))
                .andExpect(jsonPath("$.[1].homeTeamName", is("Poland")))
                .andExpect(jsonPath("$.[1].awayTeamName", is("Scotland")))
                .andExpect(jsonPath("$.[1].homeTeamFlag", is("Poland.jpg")))
                .andExpect(jsonPath("$.[1].awayTeamFlag", is("Scotland.jpg")))
                .andExpect(jsonPath("$.[1].date", is(date.getTime())));
    }

    @Test
    public void shouldGetFixtureByIdFromDAOAsJSON() throws Exception {
        when(fixtureDAO.getFixtureById("1234")).thenReturn(fixture1);

        mockMvc.perform(get("/fixtures/1234")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is("1234")))
                .andExpect(jsonPath("$.venue", is("Firhill"))).andExpect(jsonPath("$.homeTeamName", is("Scotland")))
                .andExpect(jsonPath("$.awayTeamName", is("Germany")))
                .andExpect(jsonPath("$.homeTeamFlag", is("Scotland.jpg")))
                .andExpect(jsonPath("$.awayTeamFlag", is("Germany.jpg")))
                .andExpect(jsonPath("$.date", is(date.getTime())));
    }

}
