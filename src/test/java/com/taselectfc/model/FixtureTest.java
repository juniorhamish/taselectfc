package com.taselectfc.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class FixtureTest {

    @Test
    public void validateEqualsAndHashcodeImplementation() {
        new EqualsTester()
                .addEqualityGroup(new FixtureBuilder().id("1234").build(), new FixtureBuilder().id("1234").build())
                .addEqualityGroup(new FixtureBuilder().id("5678").homeTeamName("Scotland").build(),
                        new FixtureBuilder().id("5678").homeTeamName("Germany").build())
                .testEquals();
    }

}
