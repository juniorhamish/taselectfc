package com.taselectfc.controllers;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.model.Fixture;

@Controller
public class FixtureController {

    private static Logger log = LoggerFactory.getLogger(FixtureController.class);

    @Autowired
    private FixtureDAO fixtureDAO;

    @RequestMapping(value = "/fixtures", produces = "application/json")
    @ResponseBody
    public List<Fixture> getAllFixtures(HttpSession session) throws ParseException {
        log.debug("Getting fixture list for session [{}]", session.getId());

        return fixtureDAO.getAllFixtures();
    }

    @RequestMapping(value = "/fixtures/{id}", produces = "application/json")
    @ResponseBody
    public Fixture getFixture(@PathVariable String id, HttpSession session) throws ParseException {
        log.debug("Getting fixture [{}] for session [{}]", id, session.getId());

        return fixtureDAO.getFixtureById(id);
    }
}
