package com.taselectfc.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taselectfc.model.FixtureSummary;

@Controller(value="/")
public class FixtureController {

    @RequestMapping(value="/fixtures")
    public @ResponseBody List<FixtureSummary> getAllFixtures() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        List<FixtureSummary> fixtures = new ArrayList<>();
        
        FixtureSummary fixture = new FixtureSummary();
        fixture.setHomeTeamName("Georgia");
        fixture.setHomeTeamFlag("Georgia.jpg");
        fixture.setAwayTeamName("Scotland");
        fixture.setAwayTeamFlag("Scotland.png");
        fixture.setVenue("TBC");
        fixture.setDate(formatter.parse("04-09-2015 15:00"));
        fixtures.add(fixture);
        
        fixture = new FixtureSummary();
        fixture.setHomeTeamName("Scotland");
        fixture.setHomeTeamFlag("Scotland.png");
        fixture.setAwayTeamName("Germany");
        fixture.setAwayTeamFlag("Germany.jpg");
        fixture.setVenue("TBC");
        fixture.setDate(formatter.parse("06-09-2015 15:00"));
        fixtures.add(fixture);
        
        fixture = new FixtureSummary();
        fixture.setHomeTeamName("Scotland");
        fixture.setHomeTeamFlag("Scotland.png");
        fixture.setAwayTeamName("Poland");
        fixture.setAwayTeamFlag("Poland.jpg");
        fixture.setVenue("TBC");
        fixture.setDate(formatter.parse("08-10-2015 15:00"));
        fixtures.add(fixture);
        
        fixture = new FixtureSummary();
        fixture.setHomeTeamName("Gibraltar");
        fixture.setHomeTeamFlag("Gibraltar.jpg");
        fixture.setAwayTeamName("Scotland");
        fixture.setAwayTeamFlag("Scotland.png");
        fixture.setVenue("TBC");
        fixture.setDate(formatter.parse("11-10-2015 15:00"));
        fixtures.add(fixture);
        
        return fixtures;
    }
}
