package com.taselectfc.controllers;

import static com.taselectfc.model.JsonAssertions.assertJsonContent;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taselectfc.Application;
import com.taselectfc.dao.TeamDAO;
import com.taselectfc.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class TeamControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TeamDAO teamDAO;

    private Team team1;
    private Team team2;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        team1 = new Team.Builder().name("Scotland").flagName("Scotland.jpg").build();
        team2 = new Team.Builder().name("Germany").flagName("Germany.jpg").build();
    }

    @After
    public void teardown() {
        teamDAO.deleteAll();
    }

    @Test
    public void shouldGetAllTeamsAsJson() throws Exception {
        teamDAO.save(Arrays.asList(team1, team2));

        ResultActions result = mockMvc.perform(get("/teams")).andExpect(status().isOk());

        assertJsonContent(result, team1, team2);
    }

    @Test
    public void shouldGetTeamById() throws Exception {
        team1 = teamDAO.save(team1);

        ResultActions result = mockMvc.perform(get("/teams/" + team1.getId())).andExpect(status().isOk());

        assertJsonContent(result, team1);
    }

    @Test
    public void shouldGet404IfTeamDoesNotExist() throws Exception {
        mockMvc.perform(get("/teams/345")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldDeleteFixtureAndReturnObjectAsJson() throws Exception {
        team1 = teamDAO.save(team1);

        ResultActions result = mockMvc.perform(delete("/teams/" + team1.getId())).andExpect(status().isOk());

        assertJsonContent(result, team1);
    }

    @Test
    public void shouldGet404AndNoContentOnDeleteIfTeamDoesNotExist() throws Exception {
        mockMvc.perform(delete("/teams/5678")).andExpect(status().isNotFound()).andExpect(content().string(""));
    }

    @Test
    public void shouldSaveNewFixtureAndReturnJson() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String teamAsJson = jsonMapper.writeValueAsString(team2);

        ResultActions result = mockMvc
                .perform(post("/teams/").contentType(MediaType.APPLICATION_JSON).content(teamAsJson))
                .andExpect(status().isOk());

        assertJsonContent(result, teamDAO.findAll().iterator().next());
    }

}
