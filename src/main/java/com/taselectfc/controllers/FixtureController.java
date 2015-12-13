package com.taselectfc.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.exception.DuplicateFixtureException;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;

@RestController
public class FixtureController {

    private static final Logger LOG = LoggerFactory.getLogger(FixtureController.class);

    @Autowired
    private FixtureDAO fixtureDAO;

    @RequestMapping(value = "/fixtures", produces = "application/json", method = GET)
    public Iterable<Fixture> getAllFixtures(HttpSession session) {
        LOG.debug("Getting fixture list for session [{}]", session.getId());

        Iterable<Fixture> fixtureList = fixtureDAO.findAll();
        if (fixtureList == null) {
            fixtureList = Collections.emptyList();
        }

        return fixtureList;
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json", method = GET)
    public Fixture getFixture(@PathVariable String id, HttpSession session) {
        LOG.debug("Getting fixture [{}] for session [{}]", id, session.getId());

        Fixture fixture = fixtureDAO.findOne(id);
        if (fixture == null) {
            throw new FixtureNotFoundException();
        }

        return fixture;
    }

    @RequestMapping(value = "/fixtures/{id}", method = DELETE)
    public Fixture deleteFixture(@PathVariable String id, HttpSession session) {
        LOG.debug("Deleting fixture [{}] for session [{}]", id, session.getId());

        Fixture fixture = getFixture(id, session);
        fixtureDAO.delete(fixture);

        return fixture;
    }

    @RequestMapping(value = "/fixtures", method = POST)
    public Fixture createFixture(@RequestBody Fixture fixture, HttpSession session) {
        LOG.debug("Creating fixture [{}] for session [{}]", fixture.getId(), session.getId());

        if (fixtureDAO.exists(fixture.getId())) {
            throw new DuplicateFixtureException();
        }

        return fixtureDAO.save(fixture);
    }

    @RequestMapping(value = "/fixtures/{id}", method = PUT)
    public Fixture updateOrCreateFixture(@PathVariable String id, @RequestBody Fixture fixture, HttpSession session) {
        LOG.debug("Putting fixture [{}] for session [{}]", id, session.getId());

        fixture.setId(id);

        return fixtureDAO.save(fixture);
    }
}
