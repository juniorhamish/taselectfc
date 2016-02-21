package com.taselectfc.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class PlayerTest {

    @Test
    public void validateEqualsAndHashcodeImplementation() {
        new EqualsTester().addEqualityGroup(new Player(1234L), new Player(1234L)).testEquals();
    }

    @Test
    public void shouldUseBuilderToCreatePlayer() {
        Player player = new Player.Builder().firstName("Homer").lastName("Simpson")
                .dateOfBirth(LocalDate.of(1981, 10, 21)).build();

        assertThat(player.firstName(), is("Homer"));
        assertThat(player.lastName(), is("Simpson"));
        assertThat(player.dateOfBirth(), is(LocalDate.of(1981, 10, 21)));
    }
}
