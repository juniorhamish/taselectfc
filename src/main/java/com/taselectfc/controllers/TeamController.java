package com.taselectfc.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.taselectfc.dao.TeamDAO;
import com.taselectfc.exception.TeamNotFoundException;
import com.taselectfc.model.Team;

public class TeamController {

    @Autowired
    private TeamDAO teamDAO;

    public Team getTeamById(Long id) {
        Team team = teamDAO.findOne(id);

        if (team == null) {
            throw new TeamNotFoundException(String.format("Could not find Team with ID [%s]", id));
        }

        return team;
    }

    public Iterable<Team> getAllTeams() {
        return teamDAO.findAll();
    }

    public Team delete(Long id) {
        Team team = getTeamById(id);
        teamDAO.delete(id);
        return team;
    }

    public Team save(Team team) {
        return teamDAO.save(team);
    }

}
