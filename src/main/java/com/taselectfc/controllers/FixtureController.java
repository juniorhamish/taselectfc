package com.taselectfc.controllers;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/fixtures", produces = "application/json")
    @ResponseBody
    public List<Fixture> getAllFixtures(HttpSession session) throws ParseException {
        LOG.debug("Getting fixture list for session [{}]", session.getId());

        return fixtureDAO.getAllFixtures();
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Fixture> getFixture(@PathVariable String id, HttpSession session) throws ParseException {
        LOG.debug("Getting fixture [{}] for session [{}]", id, session.getId());

        HttpStatus requestStatus = HttpStatus.OK;
        Fixture fixture = fixtureDAO.getFixtureById(id);

        if (fixture == null) {
            requestStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(fixture, requestStatus);
    }

    @RequestMapping(value = "/fixtures/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Fixture> deleteFixture(@PathVariable String id, HttpSession session) {
        LOG.debug("Deleting fixture [{}] for session [{}]", id, session.getId());

        HttpStatus requestStatus = HttpStatus.OK;
        Fixture deletedFixture = fixtureDAO.deleteFixtureById(id);

        if (deletedFixture == null) {
            requestStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(deletedFixture, requestStatus);
    }
}
