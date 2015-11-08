package com.taselectfc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.taselectfc.model.Fixture;

public class InMemoryFixtureDAO implements FixtureDAO {

    private static final Map<String, Fixture> FIXTURES = new TreeMap<>();

    @Override
    public Fixture getFixtureById(String id) {
        return FIXTURES.get(id);
    }

    @Override
    public List<Fixture> getAllFixtures() {
        List<Fixture> fixtures = new ArrayList<>();

        for (Fixture fixture : FIXTURES.values()) {
            fixtures.add(fixture);
        }

        return fixtures;
    }

    @Override
    public Fixture deleteFixtureById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Fixture create(Fixture fixture) {
        // TODO Auto-generated method stub
        return null;
    }

}
