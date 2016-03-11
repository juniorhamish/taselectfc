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
import com.taselectfc.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TeamDAOIT {

    @Autowired
    private TeamDAO teamDAO;

    @Test
    public void shouldAssignIDToTeamWhenSaved() {
        Team team = new Team.Builder().build();

        assertThat(team.getId(), is(nullValue()));

        team = teamDAO.save(team);

        assertThat(team.getId(), is(notNullValue()));
    }
}
