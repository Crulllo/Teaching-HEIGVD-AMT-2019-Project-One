package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.business.FilmsManagerLocal;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.integration.IFilmsDao;
import ch.heigvd.amt.project.model.Film;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletFilm", urlPatterns = "/film")
public class ServletFilm extends HttpServlet {

    @EJB
    IFilmsDao filmsDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = "1";
        if(request.getParameter("id") != null) {
            id = request.getParameter("id");
        }

        Film film = null;
        try {
            film = filmsDAO.findById(Long.parseLong(id));
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("film", film);
        request.getRequestDispatcher("/WEB-INF/pages/film.jsp").forward(request, response);
    }
}
