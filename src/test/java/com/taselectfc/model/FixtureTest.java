package com.taselectfc.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.testing.EqualsTester;

@RunWith(MockitoJUnitRunner.class)
public class FixtureTest {

    @Mock
    private Team team;

    @Test
    public void validateEqualsAndHashcodeImplementation() {
        new EqualsTester().addEqualityGroup(new Fixture(1234L), new Fixture(1234L))
                .addEqualityGroup(new Fixture(5678L), new Fixture(5678L)).testEquals();
    }

    @Test
    public void shouldUseBuilderToCreateFixture() {
        Fixture fixture = new Fixture.Builder().homeTeam(team).awayTeam(team)
                .kickoff(ZonedDateTime.of(2016, 10, 21, 15, 0, 0, 0, ZoneId.of("GMT"))).venue("Hampden").build();

        assertThat(fixture.getHomeTeam(), is(team));
        assertThat(fixture.getAwayTeam(), is(team));
        assertThat(fixture.getVenue(), is("Hampden"));
        assertThat(fixture.getKickoff(), is(ZonedDateTime.of(2016, 10, 21, 15, 0, 0, 0, ZoneId.of("GMT"))));
    }

}
