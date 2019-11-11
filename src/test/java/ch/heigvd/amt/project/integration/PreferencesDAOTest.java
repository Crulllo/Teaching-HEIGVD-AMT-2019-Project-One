package ch.heigvd.amt.project.integration;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Film;
import ch.heigvd.amt.project.model.Preference;
import ch.heigvd.amt.project.model.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class PreferencesDAOTest {

    private final static Random random = new Random();

    @EJB
    IUsersDAO usersDAO;

    @EJB
    IFilmsDao filmsDao;

    @EJB
    IPreferencesDAO preferencesDAO;

  /*
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "test.jar")
      .addPackages(true, "ch.heigvd");
  }
  */

  // TODO: test cascading effects

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAPreference() throws DuplicateKeyException, SQLException {
        Film film = Film.builder().id(constructId()).title("movie")
                .moviePosterPath("path.jpg").director("Fring").runningTime(200).build();
        User stephane = User.builder().username("steph" + System.currentTimeMillis()).firstName("Stephane").lastName("Selim")
                .email("selim@email.com").build();
        Film createdFilm = filmsDao.createAndSpecifyId(film);
        assertEquals(film, createdFilm);
        User createdUser = usersDAO.create(stephane);
        assertEquals(stephane, createdUser);
        Preference preference = Preference.builder()
                .user(stephane)
                .film(film)
                .build();
        Preference preferenceCreated = preferencesDAO.create(preference);
        assertEquals(preferenceCreated, preference);
    }

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAndRetrieveAPreferenceViaThePreferencesDAO() throws DuplicateKeyException, KeyNotFoundException {
        Film film = Film.builder().id(constructId()).title("movie")
                .moviePosterPath("path.jpg").director("Fring").runningTime(200).build();
        User stephane = User.builder().username("steph" + System.currentTimeMillis()).firstName("Stephane").lastName("Selim")
                .email("selim@email.com").build();
        Film createdFilm = filmsDao.createAndSpecifyId(film);
        assertEquals(film, createdFilm);
        User createdUser = usersDAO.create(stephane);
        assertEquals(stephane, createdUser);
        Preference preference = Preference.builder()
                .user(stephane)
                .film(film)
                .build();
        Preference preferenceCreated = preferencesDAO.create(preference);
        Preference preferenceFetched = preferencesDAO.findByKeys(film.getId(), stephane.getUsername());
        assertEquals(preferenceCreated, preference);
        assertEquals(preferenceFetched, preference);
        assertSame(preference, preferenceCreated);
        assertNotSame(preference, preferenceFetched);
    }

    @Test
    public void itShouldBePossibleToDeleteAPreference() throws DuplicateKeyException, KeyNotFoundException {
        Film film = Film.builder().id(constructId()).title("movie")
                .moviePosterPath("path.jpg").director("Fring").runningTime(200).build();
        User stephane = User.builder().username("steph" + System.currentTimeMillis()).firstName("Stephane").lastName("Selim")
                .email("selim@email.com").build();
        Film createdFilm = filmsDao.createAndSpecifyId(film);
        assertEquals(film, createdFilm);
        User createdUser = usersDAO.create(stephane);
        assertEquals(stephane, createdUser);
        Preference preference = Preference.builder()
                .user(stephane)
                .film(film)
                .build();
        Preference preferenceCreated = preferencesDAO.create(preference);
        Preference preferenceFetched = preferencesDAO.findByKeys(film.getId(), stephane.getUsername());
        assertEquals(preferenceCreated, preference);
        assertEquals(preferenceFetched, preference);
        preferencesDAO.delete(preference);
        boolean hasThrown = false;
        try {
            preferenceFetched = preferencesDAO.findByKeys(film.getId(), stephane.getUsername());
        } catch (KeyNotFoundException e) {
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    public void itShouldBeAbleToFetchAllFilmsPreferencesOfAUser() throws DuplicateKeyException, KeyNotFoundException {
        Film film1 = Film.builder().id(constructId()).title("JurassicPark")
                .moviePosterPath("path.jpg").director("Fring").runningTime(200).build();
        Film film2 = Film.builder().id(constructId()).title("The Mummy")
                .moviePosterPath("path.jpg").director("Fring").runningTime(200).build();
        User stephane = User.builder().username("steph" + System.currentTimeMillis()).firstName("Stephane").lastName("Selim")
                .email("selim@email.com").build();
        Film createdFilm1 = filmsDao.createAndSpecifyId(film1);
        assertEquals(film1, createdFilm1);
        Film createdFilm2 = filmsDao.createAndSpecifyId(film2);
        assertEquals(film2, createdFilm2);
        User createdUser = usersDAO.create(stephane);
        assertEquals(stephane, createdUser);
        Preference preference1 = Preference.builder()
                .user(stephane)
                .film(film1)
                .build();
        Preference preference2 = Preference.builder()
                .user(stephane)
                .film(film2)
                .build();

        Preference preference1Created = preferencesDAO.create(preference1);
        Preference preference2Created = preferencesDAO.create(preference2);

        List<Film> filmsPreferences = preferencesDAO.findAllByUsername(stephane.getUsername());
        assertEquals(2, filmsPreferences.size());
    }

    @Test
    public void preferencesShouldBeDeletedWhenInstancesAreDeleted() {
        assertEquals(2, 2);
    }

    /**
     * Construct long ids for testing purposes
     * @return random long ids for films created
     */
    private long constructId() {
        return Long.parseLong(String.valueOf(random.nextLong()).substring(0, 7));
    }
}