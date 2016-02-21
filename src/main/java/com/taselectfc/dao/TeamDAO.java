package com.taselectfc.dao;

import org.springframework.data.repository.CrudRepository;

import com.taselectfc.model.Team;

public interface TeamDAO extends CrudRepository<Team, Long> {

}
