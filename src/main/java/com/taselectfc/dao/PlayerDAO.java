package com.taselectfc.dao;

import org.springframework.data.repository.CrudRepository;

import com.taselectfc.model.Player;

public interface PlayerDAO extends CrudRepository<Player, Long> {

}
