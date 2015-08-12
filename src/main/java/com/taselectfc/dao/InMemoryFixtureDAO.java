package com.taselectfc.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.taselectfc.model.Fixture;

public class InMemoryFixtureDAO implements FixtureDAO {

    private static final Map<String, Fixture> FIXTURES = new TreeMap<>();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    static {
        try {
            Fixture fixture = new Fixture();
            fixture.setId("1");
            fixture.setHomeTeamName("Georgia");
            fixture.setHomeTeamFlag("Georgia.jpg");
            fixture.setAwayTeamName("Scotland");
            fixture.setAwayTeamFlag("Scotland.jpg");
            fixture.setVenue("TBC");
            fixture.setDate(formatter.parse("04-09-2015 15:00"));
            FIXTURES.put("1", fixture);

            fixture = new Fixture();
            fixture.setId("2");
            fixture.setHomeTeamName("Scotland");
            fixture.setHomeTeamFlag("Scotland.jpg");
            fixture.setAwayTeamName("Germany");
            fixture.setAwayTeamFlag("Germany.jpg");
            fixture.setVenue("TBC");
            fixture.setDate(formatter.parse("06-09-2015 15:00"));
            FIXTURES.put("2", fixture);

            fixture = new Fixture();
            fixture.setId("3");
            fixture.setHomeTeamName("Scotland");
            fixture.setHomeTeamFlag("Scotland.jpg");
            fixture.setAwayTeamName("Poland");
            fixture.setAwayTeamFlag("Poland.jpg");
            fixture.setVenue("TBC");
            fixture.setDate(formatter.parse("08-10-2015 15:00"));
            FIXTURES.put("3", fixture);

            fixture = new Fixture();
            fixture.setId("4");
            fixture.setHomeTeamName("Gibraltar");
            fixture.setHomeTeamFlag("Gibraltar.jpg");
            fixture.setAwayTeamName("Scotland");
            fixture.setAwayTeamFlag("Scotland.jpg");
            fixture.setVenue("TBC");
            fixture.setDate(formatter.parse("11-10-2015 15:00"));
            FIXTURES.put("4", fixture);
        } catch (ParseException e) {
        }
    }

    @Override
    public Fixture getFixtureById(String id) {
        return FIXTURES.get(id);
    }

    @Override
    public List<Fixture> getAllFixtures() {
        List<Fixture> fixtures = new ArrayList<>();

        for (Fixture fixture : FIXTURES.values()) {
            fixtures.add(fixture);
        }

        return fixtures;
    }

}
