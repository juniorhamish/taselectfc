package com.taselectfc.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class TeamTest {

    @Test
    public void validateEqualsAndHashcodeImplementation() {
        new EqualsTester().addEqualityGroup(new Team(1234L), new Team(1234L)).testEquals();
    }

    @Test
    public void shouldUseBuilderToCreateTeam() {
        Team team = new Team.Builder().name("Scotland").flagName("Scotland.jpg").build();

        assertThat(team.getName(), is("Scotland"));
        assertThat(team.getFlagName(), is("Scotland.jpg"));
    }

}
