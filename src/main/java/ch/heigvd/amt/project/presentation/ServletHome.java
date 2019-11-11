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
    IFilmsDao filmsDAO;

    @EJB
    IUsersDAO usersDAO;

    @EJB
    IPreferencesDAO preferencesDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int filmsPerPage = 8;
        List<Film> films = null;
        int nbPages = 0;

        try {
            int nbFilms = filmsDAO.findAll().size();
            films = filmsDAO.findBetween((page - 1) * filmsPerPage, page * filmsPerPage);
            nbPages = (int) Math.ceil(nbFilms * 1.0 / filmsPerPage);

            String username = (String)request.getSession().getAttribute("principal");
            User user = usersDAO.findById(username);
            // User liked a film, adding preference to db
            if(request.getParameter("filmId") != null) {
                int filmId = Integer.parseInt(request.getParameter("filmId"));
                Film film = filmsDAO.findById((long)filmId);
                Preference pref = Preference.builder().user(user).film(film).build();
                try {
                    preferencesDAO.create(pref);
                } catch (DuplicateKeyException e) {
                    e.printStackTrace();
                }
            }
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("films", films);
        request.setAttribute("nbPages", nbPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
