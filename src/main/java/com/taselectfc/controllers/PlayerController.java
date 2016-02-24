package com.taselectfc.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.taselectfc.dao.PlayerDAO;
import com.taselectfc.exception.PlayerNotFoundException;
import com.taselectfc.model.Player;

public class PlayerController {

    @Autowired
    private PlayerDAO playerDAO;

    public Player getPlayerById(Long id) {
        Player player = playerDAO.findOne(id);

        if (player == null) {
            throw new PlayerNotFoundException(String.format("Could not find Player with ID [%s]", id));
        }

        return player;
    }

    public Iterable<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    public Player delete(Long id) {
        Player player = getPlayerById(id);
        playerDAO.delete(id);

        return player;
    }

    public Player save(Player player) {
        return playerDAO.save(player);
    }

}
