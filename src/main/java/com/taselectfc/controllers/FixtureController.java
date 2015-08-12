package com.taselectfc.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.model.Fixture;

@Controller
public class FixtureController {

    @Autowired
    private FixtureDAO fixtureDAO;

    @RequestMapping(value = "/fixtures")
    public @ResponseBody List<Fixture> getAllFixtures() throws ParseException {
        return fixtureDAO.getAllFixtures();
    }

    @RequestMapping(value = "/fixtures/{id}")
    public @ResponseBody Fixture getFixture(@PathVariable String id) throws ParseException {
        return fixtureDAO.getFixtureById(id);
    }
}
