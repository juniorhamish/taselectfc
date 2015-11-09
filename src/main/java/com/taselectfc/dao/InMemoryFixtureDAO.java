package com.taselectfc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.taselectfc.exception.DuplicateFixtureException;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;

public class InMemoryFixtureDAO implements FixtureDAO {

    private Map<String, Fixture> fixtures = new TreeMap<>();

    @Override
    public Fixture getFixtureById(String id) {
        Fixture fixture = fixtures.get(id);

        if (fixture == null) {
            throw new FixtureNotFoundException();
        }

        return fixture;
    }

    @Override
    public List<Fixture> getAllFixtures() {
        List<Fixture> allFixtures = new ArrayList<>();

        for (Fixture fixture : fixtures.values()) {
            allFixtures.add(fixture);
        }

        return allFixtures;
    }

    @Override
    public Fixture deleteFixtureById(String id) {
        Fixture deletedFixture = fixtures.remove(id);

        if (deletedFixture == null) {
            throw new FixtureNotFoundException();
        }

        return deletedFixture;
    }

    @Override
    public void create(Fixture fixture) {
        if (fixtures.containsKey(fixture.getId())) {
            throw new DuplicateFixtureException();
        }

        fixtures.put(fixture.getId(), fixture);
    }

    @Override
    public boolean exists(String id) {
        return fixtures.containsKey(id);
    }

    @Override
    public void save(Fixture fixture) {
        fixtures.put(fixture.getId(), fixture);
    }

}
