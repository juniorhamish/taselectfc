package com.taselectfc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.taselectfc.dao.TeamDAO;
import com.taselectfc.exception.TeamNotFoundException;
import com.taselectfc.model.Team;

@RestController
public class TeamController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamDAO teamDAO;

    @RequestMapping(value = "/teams/{id}", produces = "application/json", method = RequestMethod.GET)
    public Team getTeamById(@PathVariable Long id) {
        LOG.debug("Getting team [{}] for session [{}]", id, getSessionId());

        Team team = teamDAO.findOne(id);
        if (team == null) {
            throw new TeamNotFoundException(String.format("Could not find Team with ID [%s]", id));
        }

        return team;
    }

    @RequestMapping(value = "/teams", produces = "application/json", method = RequestMethod.GET)
    public Iterable<Team> getAllTeams() {
        LOG.debug("Getting all teams for session [{}]", getSessionId());

        return teamDAO.findAll();
    }

    @RequestMapping(value = "/teams/{id}", produces = "application/json", method = RequestMethod.DELETE)
    public Team delete(@PathVariable Long id) {
        LOG.debug("Deleting team [{}] for session [{}]", id, getSessionId());

        Team team = getTeamById(id);
        teamDAO.delete(id);

        return team;
    }

    @RequestMapping(value = "/teams", produces = "application/json", method = RequestMethod.POST)
    public Team save(@RequestBody Team team) {
        LOG.debug("Creating team [{}] for session [{}]", team.getId(), getSessionId());

        return teamDAO.save(team);
    }

    private static String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

}
