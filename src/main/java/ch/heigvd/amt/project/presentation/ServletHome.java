package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.business.FilmsManagerLocal;
import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.integration.IFilmsDao;
import ch.heigvd.amt.project.integration.IPreferencesDAO;
import ch.heigvd.amt.project.integration.IUsersDAO;
import ch.heigvd.amt.project.model.Film;
import ch.heigvd.amt.project.model.Preference;
import ch.heigvd.amt.project.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


//TODO: Page navigation
@WebServlet(name = "ServletHome", urlPatterns = "/home")
public class ServletHome extends HttpServlet {

    @EJB
    FilmsManagerLocal filmsManager;

    @EJB
    IUsersDAO usersDAO;
    IPreferencesDAO preferencesDAO;

    final int FILMS_PER_PAGE = 8;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Film> films = filmsManager.getAllFilms();

        List<Film> films = new LinkedList<>();
        try {
            films = filmsDAO.findAll();
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("films", films);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
      
      /*int page = 1;
        int nbFilmsPerPage = 8;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Film> films = filmsManager.getFilmsBetween((page - 1) * FILMS_PER_PAGE, page * FILMS_PER_PAGE);
        int nbFilms = filmsManager.getAllFilms().size();
        int nbPages = (int) Math.ceil(nbFilms * 1.0 / FILMS_PER_PAGE);

        int filmId = 0;
        try {
            String username = (String)request.getSession().getAttribute("principal");
            User user = usersDAO.findById(username);
            // User liked a film, adding preference to db
            if(request.getParameter("idFilm") != null) {
                filmId = Integer.parseInt(request.getParameter("filmId"));
                Film film = filmsManager.getFilm(filmId);
                Preference pref = Preference.builder().user(user).film(film).build();
                try {
                    preferencesDAO.create(pref);
                } catch (DuplicateKeyException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("filmId", filmId);
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("films", films);
        request.setAttribute("nbPages", nbPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);*/
    }
}
