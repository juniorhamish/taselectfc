package com.taselectfc.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;

@RestController
public class FixtureController {

    private static final Logger LOG = LoggerFactory.getLogger(FixtureController.class);

    @Autowired
    private FixtureDAO fixtureDAO;

    @RequestMapping(value = "/fixtures", produces = "application/json", method = GET)
    public Iterable<Fixture> getAllFixtures() {
        LOG.debug("Getting fixture list for session [{}]", getSessionId());

        return fixtureDAO.findAll();
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json", method = GET)
    public Fixture getFixtureById(@PathVariable String id) {
        LOG.debug("Getting fixture [{}] for session [{}]", id, getSessionId());

        Fixture fixture = fixtureDAO.findOne(Long.valueOf(id));
        if (fixture == null) {
            throw new FixtureNotFoundException(String.format("Could not find Fixture with ID [%s]", id));
        }

        return fixture;
    }

    @RequestMapping(value = "/fixtures/{id}", method = DELETE)
    public Fixture deleteFixture(@PathVariable String id) {
        LOG.debug("Deleting fixture [{}] for session [{}]", id, getSessionId());

        Fixture fixture = getFixtureById(id);
        fixtureDAO.delete(Long.valueOf(id));

        return fixture;
    }

    @RequestMapping(value = "/fixtures", method = POST)
    public Fixture createFixture(@RequestBody Fixture fixture) {
        LOG.debug("Creating fixture [{}] for session [{}]", fixture.getId(), getSessionId());

        return fixtureDAO.save(fixture);
    }

    private static String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }
}
