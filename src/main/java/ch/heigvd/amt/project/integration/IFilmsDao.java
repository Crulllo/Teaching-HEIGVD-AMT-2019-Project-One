package ch.heigvd.amt.project.integration;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Film;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IFilmsDao extends IDAO<Long, Film> {
    List<Film> findFrom(long id, long limit) throws KeyNotFoundException;

    /**
     * Used for testing purposes only. Ids are auto managed by the db.
     * @param film the film we create
     * @return film created
     * @throws DuplicateKeyException if another instance with same id already exists in db
     */
    Film createAndSpecifyId(Film film) throws DuplicateKeyException;
}
