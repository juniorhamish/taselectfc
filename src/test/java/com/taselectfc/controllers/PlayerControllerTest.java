package com.taselectfc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.taselectfc.dao.PlayerDAO;
import com.taselectfc.exception.PlayerNotFoundException;
import com.taselectfc.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {

    @Mock
    private PlayerDAO playerDAO;

    @InjectMocks
    private PlayerController playerController;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private RequestAttributes requestAttributes;

    @Before
    public void setup() {
        when(requestAttributes.getSessionId()).thenReturn("MockSession");
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    @After
    public void teardown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void shouldGetPlayerByIdUsingDAO() {
        Player player = new Player.Builder().firstName("David").lastName("Johnston").build();
        when(playerDAO.findOne(1L)).thenReturn(player);

        Player foundPlayer = playerController.getPlayerById(1L);

        assertThat(foundPlayer, is(player));
    }

    @Test
    public void shouldThrowExceptionIfPlayerNotFound() {
        when(playerDAO.findOne(2L)).thenReturn(null);

        expectedException.expect(PlayerNotFoundException.class);
        expectedException.expectMessage(containsString("2"));

        playerController.getPlayerById(2L);
    }

    @Test
    public void shouldGetAllPlayersFromDAO() {
        Player player1 = new Player.Builder().firstName("David").lastName("Johnston").build();
        Player player2 = new Player.Builder().firstName("Homer").lastName("Simpson").build();
        when(playerDAO.findAll()).thenReturn(Arrays.asList(player1, player2));

        Iterable<Player> allPlayers = playerController.getAllPlayers();

        assertThat(allPlayers, contains(player1, player2));
    }

    @Test
    public void shouldDeletePlayerUsingDAO() {
        when(playerDAO.findOne(3L)).thenReturn(new Player.Builder().build());

        playerController.delete(3L);

        verify(playerDAO).delete(3L);
    }

    @Test
    public void shouldReturnDeletedPlayer() {
        Player player = new Player.Builder().firstName("David").lastName("Johnston").build();
        when(playerDAO.findOne(4L)).thenReturn(player);

        Player deletedPlayer = playerController.delete(4L);

        assertThat(deletedPlayer, is(player));
    }

    @Test
    public void shouldThrowExceptionIfPlayerToDeleteDoesNotExist() {
        when(playerDAO.findOne(8L)).thenReturn(null);

        expectedException.expect(PlayerNotFoundException.class);
        expectedException.expectMessage(containsString("8"));

        playerController.delete(8L);
    }

    @Test
    public void shouldSavePlayerUsingDAO() {
        Player player = new Player.Builder().firstName("David").lastName("johnston").build();

        playerController.save(player);

        verify(playerDAO).save(player);
    }

    @Test
    public void shouldReturnSavedPlayer() {
        Player savedPlayer = new Player.Builder().firstName("Not").lastName("Saved").build();
        Player playerToSave = new Player.Builder().firstName("Already").lastName("Saved").build();
        when(playerDAO.save(playerToSave)).thenReturn(savedPlayer);

        Player returnedPlayer = playerController.save(playerToSave);

        assertThat(returnedPlayer, is(savedPlayer));
    }
}
