package com.taselectfc.model;

import java.time.ZonedDateTime;

public class FixtureBuilder {

    private String id;
    private ZonedDateTime date;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamFlag;
    private String awayTeamFlag;
    private String venue;

    public FixtureBuilder id(String id) {
        this.id = id;

        return this;
    }

    public FixtureBuilder date(ZonedDateTime kickOff) {
        this.date = kickOff;

        return this;
    }

    public FixtureBuilder homeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;

        return this;
    }

    public FixtureBuilder awayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;

        return this;
    }

    public FixtureBuilder homeTeamFlag(String homeTeamFlag) {
        this.homeTeamFlag = homeTeamFlag;

        return this;
    }

    public FixtureBuilder awayTeamFlag(String awayTeamFlag) {
        this.awayTeamFlag = awayTeamFlag;

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
        fixture.setHomeTeamName(homeTeamName);
        fixture.setAwayTeamName(awayTeamName);
        fixture.setHomeTeamFlag(homeTeamFlag);
        fixture.setAwayTeamFlag(awayTeamFlag);
        fixture.setVenue(venue);

        return fixture;
    }
}
