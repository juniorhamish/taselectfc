package com.taselectfc.controllers;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.model.Fixture;

@Controller
public class FixtureController {

    private static final Logger LOG = LoggerFactory.getLogger(FixtureController.class);

    @Autowired
    private FixtureDAO fixtureDAO;

    @Autowired
    private IdGenerator idGenerator;

    @RequestMapping(value = "/fixtures", produces = "application/json")
    @ResponseBody
    public List<Fixture> getAllFixtures(HttpSession session) throws ParseException {
        LOG.debug("Getting fixture list for session [{}]", session.getId());

        List<Fixture> fixtureList = fixtureDAO.getAllFixtures();
        if (fixtureList == null) {
            fixtureList = Collections.emptyList();
        }
        return fixtureList;
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json")
    @ResponseBody
    public Fixture getFixture(@PathVariable String id, HttpSession session) throws ParseException {
        LOG.debug("Getting fixture [{}] for session [{}]", id, session.getId());

        return fixtureDAO.getFixtureById(id);
    }

    @RequestMapping(value = "/fixtures/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Fixture deleteFixture(@PathVariable String id, HttpSession session) {
        LOG.debug("Deleting fixture [{}] for session [{}]", id, session.getId());

        return fixtureDAO.deleteFixtureById(id);
    }

    @RequestMapping(value = "/fixtures", method = RequestMethod.POST)
    @ResponseBody
    public Fixture saveFixture(@RequestBody Fixture fixture, HttpSession session) {
        LOG.debug("Saving fixture [{}] for session [{}]", fixture.getId(), session.getId());

        if (fixture.getId() == null) {
            fixture.setId(idGenerator.generateId().toString());
        }

        return fixtureDAO.create(fixture);
    }
}
