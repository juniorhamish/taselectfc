package com.taselectfc.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.exception.BadRequestException;
import com.taselectfc.model.Fixture;

@Controller
public class FixtureController {

    private static final Logger LOG = LoggerFactory.getLogger(FixtureController.class);

    @Autowired
    private FixtureDAO fixtureDAO;

    @Autowired
    private IdGenerator idGenerator;

    @RequestMapping(value = "/fixtures", produces = "application/json", method = GET)
    @ResponseBody
    public List<Fixture> getAllFixtures(HttpSession session) throws ParseException {
        LOG.debug("Getting fixture list for session [{}]", session.getId());

        List<Fixture> fixtureList = fixtureDAO.getAllFixtures();
        if (fixtureList == null) {
            fixtureList = Collections.emptyList();
        }
        return fixtureList;
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json", method = GET)
    @ResponseBody
    public Fixture getFixture(@PathVariable String id, HttpSession session) throws ParseException {
        LOG.debug("Getting fixture [{}] for session [{}]", id, session.getId());

        return fixtureDAO.getFixtureById(id);
    }

    @RequestMapping(value = "/fixtures/{id}", method = DELETE)
    @ResponseBody
    public Fixture deleteFixture(@PathVariable String id, HttpSession session) {
        LOG.debug("Deleting fixture [{}] for session [{}]", id, session.getId());

        return fixtureDAO.deleteFixtureById(id);
    }

    @RequestMapping(value = "/fixtures", method = POST)
    @ResponseBody
    public Fixture createFixture(@RequestBody Fixture fixture, HttpSession session) {
        LOG.debug("Creating fixture [{}] for session [{}]", fixture.getId(), session.getId());

        if (fixture.getId() == null) {
            LOG.debug("New fixture assigned ID [{}] for session [{}]", fixture.getId(), session.getId());

            fixture.setId(idGenerator.generateId().toString());
        }

        fixtureDAO.create(fixture);

        return fixture;
    }

    @RequestMapping(value = "/fixtures/{id}", method = PUT)
    @ResponseBody
    public Fixture updateOrCreateFixture(@PathVariable String id, @RequestBody Fixture fixture, HttpSession session) {
        LOG.debug("Putting fixture [{}] for session [{}]", id, session.getId());

        if (fixture.getId() == null) {
            fixture.setId(id);
        } else if (!id.equals(fixture.getId())) {
            LOG.warn("Attempting to PUT fixture [{}] at URL [{}] for session [{}]", fixture.getId(), id,
                    session.getId());

            throw new BadRequestException(
                    String.format("PUT to /fixtures/%s but content has ID %s", id, fixture.getId()));
        }

        if (fixtureDAO.exists(id)) {
            fixtureDAO.save(fixture);
        } else {
            fixtureDAO.create(fixture);
        }

        return fixture;
    }
}
