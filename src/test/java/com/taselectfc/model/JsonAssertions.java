package com.taselectfc.model;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.format.DateTimeFormatter;

import org.springframework.test.web.servlet.ResultActions;

public class JsonAssertions {

    public static void assertJsonContent(ResultActions result, Team... expectedTeams) throws Exception {
        String teamPath = "$";
        if (expectedTeams.length > 1) {
            teamPath += ".[%s]";
        }

        for (int i = 0; i < expectedTeams.length; i++) {
            Team team = expectedTeams[i];
            String currentTeamPath = String.format(teamPath, i);

            assertTeamJson(result, currentTeamPath, team);
        }
    }

    public static void assertJsonContent(ResultActions result, Fixture... expectedFixtures) throws Exception {
        String fixturePath = "$";
        if (expectedFixtures.length > 1) {
            fixturePath += ".[%s]";
        }

        for (int i = 0; i < expectedFixtures.length; i++) {
            Fixture fixture = expectedFixtures[i];
            String currentFixturePath = String.format(fixturePath, i);

            assertFixtureJson(result, currentFixturePath, fixture);
            assertTeamJson(result, currentFixturePath + ".homeTeam", fixture.getHomeTeam());
            assertTeamJson(result, currentFixturePath + ".awayTeam", fixture.getAwayTeam());

        }
    }

    private static void assertFixtureJson(ResultActions result, String jsonPath, Fixture fixture) throws Exception {
        result.andExpect(jsonPath(jsonPath + ".id", is(fixture.getId().intValue())));
        result.andExpect(jsonPath(jsonPath + ".venue", is(fixture.getVenue())));

        if (fixture.getKickoff() != null) {
            result.andExpect(jsonPath(jsonPath + ".kickoff",
                    is(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(fixture.getKickoff()))));
        }
    }

    private static void assertTeamJson(ResultActions result, String teamJsonPath, Team team) throws Exception {
        if (team != null) {
            result.andExpect(jsonPath(teamJsonPath + ".id", is(team.getId().intValue())))
                    .andExpect(jsonPath(teamJsonPath + ".name", is(team.getName())))
                    .andExpect(jsonPath(teamJsonPath + ".flagName", is(team.getFlagName())));
        }
    }
}
