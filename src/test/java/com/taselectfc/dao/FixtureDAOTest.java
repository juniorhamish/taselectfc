package com.taselectfc.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.taselectfc.exception.DuplicateFixtureException;
import com.taselectfc.exception.FixtureNotFoundException;
import com.taselectfc.model.Fixture;
import com.taselectfc.model.FixtureBuilder;

@RunWith(Parameterized.class)
public class FixtureDAOTest {

    private FixtureDAO fixtureDao;
    private Fixture fixture1;
    private Fixture fixture2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public FixtureDAOTest(Supplier<FixtureDAO> fixtureDaoSupplier) {
        this.fixtureDao = fixtureDaoSupplier.get();
    }

    @Before
    public void setup() {
        fixture1 = new FixtureBuilder().id("1234").homeTeamName("Scotland").awayTeamName("Germany").build();
        fixture2 = new FixtureBuilder().id("5678").homeTeamName("Partick Thistle").awayTeamName("Aberdeen").build();
    }

    @Test
    public void shouldBeAbleToAddNewFixture() {
        assertFalse(fixtureDao.exists(fixture1.getId()));

        fixtureDao.create(fixture1);

        assertTrue(fixtureDao.exists(fixture1.getId()));
    }

    @Test
    public void shouldBeAbleToGetExistingFixtureById() {
        fixtureDao.create(fixture1);

        assertThat(fixtureDao.getFixtureById("1234"), is(fixture1));
    }

    @Test
    public void shouldThrowFixtureNotFoundExceptionIfGettingAFixtureThatDoesNotExist() {
        exception.expect(FixtureNotFoundException.class);

        fixtureDao.getFixtureById("324234234");
    }

    @Test
    public void shouldGetEmptyCollectionIfNoFixturesExist() {
        assertThat(fixtureDao.getAllFixtures(), is(empty()));
    }

    @Test
    public void shouldGetAllFixturesThatHaveBeenAddedSoFar() {
        fixtureDao.create(fixture1);
        fixtureDao.create(fixture2);

        assertThat(fixtureDao.getAllFixtures(), containsInAnyOrder(fixture1, fixture2));
    }

    @Test
    public void shouldRemoveFixtureWhenItIsDeleted() {
        fixtureDao.create(fixture1);

        fixtureDao.deleteFixtureById(fixture1.getId());

        assertFalse(fixtureDao.exists(fixture1.getId()));
    }

    @Test
    public void shouldThrowFixtureNotFoundExceptionIfFixtureDoesNotExistOnDelete() {
        exception.expect(FixtureNotFoundException.class);

        fixtureDao.deleteFixtureById("45234523453");
    }

    @Test
    public void shouldGetFixtureWithMatchingIdWhenDeleted() {
        fixtureDao.create(fixture1);

        assertThat(fixtureDao.deleteFixtureById(fixture1.getId()), is(fixture1));
    }

    @Test
    public void shouldThrowDuplicateFixtureExceptionIfCreatingAFixtureWithSameIdAsExistingFixture() {
        fixtureDao.create(fixture1);

        exception.expect(DuplicateFixtureException.class);

        fixtureDao.create(fixture1);
    }

    @Test
    public void shouldOverwriteExistingFixtureWithNewFixture() {
        Fixture newFixture = new FixtureBuilder().id(fixture1.getId()).homeTeamName("Poland").build();
        fixtureDao.create(fixture1);

        fixtureDao.save(newFixture);

        assertThat(fixtureDao.getFixtureById(fixture1.getId()).getHomeTeamName(), is("Poland"));
    }

    @Test
    public void shouldSaveNewFixtureEvenIfNoFixtureWithSameIdExists() {
        fixtureDao.save(fixture1);

        assertThat(fixtureDao.getFixtureById(fixture1.getId()).getHomeTeamName(), is("Scotland"));
    }

    @Parameters
    public static Iterable<Object[]> implementationsToTest() {
        Supplier<FixtureDAO> inMemoryImplementation = () -> new InMemoryFixtureDAO();

        return Arrays.asList(new Object[][] { { inMemoryImplementation } });
    }

}
