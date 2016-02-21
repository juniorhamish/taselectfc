package com.taselectfc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.taselectfc.dao.TeamDAO;
import com.taselectfc.exception.TeamNotFoundException;
import com.taselectfc.model.Team;

@RunWith(MockitoJUnitRunner.class)
public class TeamControllerTest {

    @Mock
    private TeamDAO teamDAO;

    @InjectMocks
    private TeamController teamController;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGetTeamByIdUsingDAO() {
        Team team = new Team.Builder().name("TestTeam").build();
        when(teamDAO.findOne(1L)).thenReturn(team);

        Team foundTeam = teamController.getTeamById(1L);

        assertThat(foundTeam, is(team));
    }

    @Test
    public void shouldThrowExceptionIfTeamNotFound() {
        when(teamDAO.findOne(2L)).thenReturn(null);

        expectedException.expect(TeamNotFoundException.class);
        expectedException.expectMessage(containsString("2"));

        teamController.getTeamById(2L);
    }

    @Test
    public void shouldGetAllTeamsFromDAO() {
        Team team1 = new Team.Builder().name("Team1").build();
        Team team2 = new Team.Builder().name("Team2").build();
        when(teamDAO.findAll()).thenReturn(Arrays.asList(team1, team2));

        Iterable<Team> allTeams = teamController.getAllTeams();

        assertThat(allTeams, contains(team1, team2));
    }

    @Test
    public void shouldDeleteTeamUsingDAO() {
        when(teamDAO.findOne(3L)).thenReturn(new Team.Builder().build());

        teamController.delete(3L);

        verify(teamDAO).delete(3L);
    }

    @Test
    public void shouldReturnDeletedTeam() {
        Team team = new Team.Builder().name("Team Name").build();
        when(teamDAO.findOne(4L)).thenReturn(team);

        Team deletedTeam = teamController.delete(4L);

        assertThat(deletedTeam, is(team));
    }

    @Test
    public void shouldThrowExceptionIfTeamToDeleteDoesNotExist() {
        when(teamDAO.findOne(8L)).thenReturn(null);

        expectedException.expect(TeamNotFoundException.class);
        expectedException.expectMessage(containsString("8"));

        teamController.delete(8L);
    }

    @Test
    public void shouldSaveTeamUsingDAO() {
        Team team = new Team.Builder().name("My Team").build();

        teamController.save(team);

        verify(teamDAO).save(team);
    }

    @Test
    public void shouldReturnSavedTeam() {
        Team savedTeam = new Team.Builder().name("I have been saved").build();
        Team teamToSave = new Team.Builder().name("I need to be saved").build();
        when(teamDAO.save(teamToSave)).thenReturn(savedTeam);

        Team returnedTeam = teamController.save(teamToSave);

        assertThat(returnedTeam, is(savedTeam));
    }
}
