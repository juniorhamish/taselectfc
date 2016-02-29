package com.taselectfc.model;

import java.time.ZonedDateTime;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = Fixture.class)
public interface FixtureProjection {

    Long getId();

    String getVenue();

    ZonedDateTime getKickoff();

    Team getHomeTeam();

    Team getAwayTeam();

}
