package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.integration.IPreferencesDAO;
import ch.heigvd.amt.project.integration.IUsersDAO;
import ch.heigvd.amt.project.model.Film;
import ch.heigvd.amt.project.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletUser", urlPatterns = "/user")
public class ServletUser extends HttpServlet {

    @EJB
    IPreferencesDAO preferencesDAO;

    @EJB
    IUsersDAO usersDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String)request.getSession().getAttribute("principal");
        User user = null;
        List<Film> films = null;

        if (username == null) {
            response.sendRedirect("/login");
        } else {
            try {
                user = usersDAO.findById(username);
                films = preferencesDAO.findAllByUsername(username);
            } catch (KeyNotFoundException e) {
                e.printStackTrace();
            }
            request.setAttribute("user", user);
            request.setAttribute("films", films);
            request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
        }
    }
}
