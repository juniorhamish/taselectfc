package com.taselectfc.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taselectfc.Application;
import com.taselectfc.model.Fixture;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class FixtureDAOIT {

    @Autowired
    private FixtureDAO fixtureDAO;

    @Test
    public void shouldAssignIDToFixtureWhenSaved() {
        Fixture fixture = new Fixture.Builder().build();

        assertThat(fixture.getId(), is(nullValue()));

        fixture = fixtureDAO.save(fixture);

        assertThat(fixture.getId(), is(notNullValue()));
    }
}
