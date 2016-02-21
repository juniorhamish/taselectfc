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
import com.taselectfc.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class PlayerDAOIT {

    @Autowired
    private PlayerDAO playerDAO;

    @Test
    public void shouldAssignIDToPlayerWhenSaved() {
        Player player = new Player.Builder().firstName("Bart").lastName("Simpson").build();

        assertThat(player.getId(), is(nullValue()));

        player = playerDAO.save(player);

        assertThat(player.getId(), is(notNullValue()));
    }
}
