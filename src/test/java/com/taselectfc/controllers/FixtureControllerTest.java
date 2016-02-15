package com.taselectfc.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.taselectfc.dao.FixtureDAO;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;

@RunWith(MockitoJUnitRunner.class)
public class FixtureControllerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private FixtureDAO fixtureDAO;

    @InjectMocks
    private FixtureController fixtureController;

    @Mock
    private HttpSession session;
    @Mock
    private Fixture fixture1;
    @Mock
    private Fixture fixture2;

    @Before
    public void setup() {
        when(session.getId()).thenReturn("1");
    }

    @Test
    public void shouldGetAllFixturesFromDAO() {
        when(fixtureDAO.findAll()).thenReturn(Arrays.asList(fixture1, fixture2));

        assertThat(fixtureController.getAllFixtures(session), contains(fixture1, fixture2));
    }

    @Test
    public void shouldGetFixtureByIdFromDAO() {
        when(fixtureDAO.findOne("1")).thenReturn(fixture1);

        assertThat(fixtureController.getFixture("1", session), is(fixture1));
    }

    @Test
    public void shouldThrowExceptionIfFixtureDoesNotExist() {
        when(fixtureDAO.findOne("1")).thenReturn(null);

        exception.expect(FixtureNotFoundException.class);

        fixtureController.getFixture("1", session);
    }

    @Test
    public void shouldDeleteFixtureFromDAO() {
        when(fixtureDAO.findOne("3")).thenReturn(fixture1);

        fixtureController.deleteFixture("3", session);

        verify(fixtureDAO).delete(fixture1);
    }

    @Test
    public void shouldThrowExceptionIfFixtureToDeleteDoesNotExist() {
        when(fixtureDAO.findOne("5")).thenReturn(null);

        exception.expect(FixtureNotFoundException.class);

        fixtureController.deleteFixture("5", session);
    }

    @Test
    public void shouldSaveFixtureOnCreate() {
        when(fixture1.getId()).thenReturn("7");
        fixtureController.createFixture(fixture1, session);

        verify(fixtureDAO).save(fixture1);
    }

}
