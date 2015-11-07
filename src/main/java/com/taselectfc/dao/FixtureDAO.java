package com.taselectfc.dao;

import java.util.List;

import com.taselectfc.model.Fixture;

public interface FixtureDAO {

    Fixture getFixtureById(String id);

    List<Fixture> getAllFixtures();

    Fixture deleteFixtureById(String id);
}
