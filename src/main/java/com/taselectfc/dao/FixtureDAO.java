package com.taselectfc.dao;

import org.springframework.data.repository.CrudRepository;

import com.taselectfc.model.Fixture;

public interface FixtureDAO extends CrudRepository<Fixture, String> {

}
