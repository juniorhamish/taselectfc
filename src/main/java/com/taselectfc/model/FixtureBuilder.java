package com.taselectfc.model;

import java.time.ZonedDateTime;

public class FixtureBuilder {

    private String id;
    private ZonedDateTime date;
    private Team homeTeam;
    private Team awayTeam;
    private String venue;

    public FixtureBuilder id(String id) {
        this.id = id;

        return this;
    }

    public FixtureBuilder date(ZonedDateTime kickOff) {
        this.date = kickOff;

        return this;
    }

    public FixtureBuilder homeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;

        return this;
    }

    public FixtureBuilder awayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;

        return this;
    }

    public FixtureBuilder venue(String venue) {
        this.venue = venue;

        return this;
    }

    public Fixture build() {
        Fixture fixture = new Fixture();
        fixture.setId(id);
        fixture.setKickoff(date);
        fixture.setHomeTeam(homeTeam);
        fixture.setAwayTeam(awayTeam);
        fixture.setVenue(venue);

        return fixture;
    }
}
