package com.taselectfc.controllers;

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
    private Fixture fixture1;
    @Mock
    private Fixture fixture2;

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
    public void shouldGetAllFixturesFromDAO() {
        when(fixtureDAO.findAll()).thenReturn(Arrays.asList(fixture1, fixture2));

        assertThat(fixtureController.getAllFixtures(), contains(fixture1, fixture2));
    }

    @Test
    public void shouldGetFixtureByIdFromDAO() {
        when(fixtureDAO.findOne(1L)).thenReturn(fixture1);

        assertThat(fixtureController.getFixtureById(1L), is(fixture1));
    }

    @Test
    public void shouldThrowExceptionIfFixtureDoesNotExist() {
        when(fixtureDAO.findOne(1L)).thenReturn(null);

        exception.expect(FixtureNotFoundException.class);
        exception.expectMessage("1");

        fixtureController.getFixtureById(1L);
    }

    @Test
    public void shouldDeleteFixtureFromDAO() {
        when(fixtureDAO.findOne(3L)).thenReturn(fixture1);

        fixtureController.deleteFixture(3L);

        verify(fixtureDAO).delete(3L);
    }

    @Test
    public void shouldThrowExceptionIfFixtureToDeleteDoesNotExist() {
        when(fixtureDAO.findOne(5L)).thenReturn(null);

        exception.expect(FixtureNotFoundException.class);
        exception.expectMessage("5");

        fixtureController.deleteFixture(5L);
    }

    @Test
    public void shouldSaveFixtureOnCreate() {
        when(fixture1.getId()).thenReturn(7L);
        fixtureController.createFixture(fixture1);

        verify(fixtureDAO).save(fixture1);
    }

}
