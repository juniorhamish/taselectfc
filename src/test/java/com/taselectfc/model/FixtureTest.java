package com.taselectfc.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class FixtureTest {

    @Test
    public void validateEqualsAndHashcodeImplementation() {
        new EqualsTester().addEqualityGroup(new Fixture(1234L), new Fixture(1234L))
                .addEqualityGroup(new Fixture(5678L), new Fixture(5678L)).testEquals();
    }

}
