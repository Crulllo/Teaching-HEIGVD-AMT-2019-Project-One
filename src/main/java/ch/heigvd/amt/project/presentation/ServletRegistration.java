package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.integration.IUsersDAO;
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

@WebServlet(name = "ServletRegistration", urlPatterns = "/register")
public class ServletRegistration extends HttpServlet {

    @EJB
    IUsersDAO usersDAO;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        List<String> errors = new LinkedList<>();

        if(username == null || username.isEmpty()) {
            errors.add("Please enter username");
        }

        if(email == null || email.isEmpty()) {
            errors.add("Please enter email");
        }

        if(lastName == null || lastName.isEmpty()) {
            errors.add("Please enter lastName");
        }

        if(password == null || password.isEmpty()) {
            errors.add("Please enter password");
        }

        if(passwordConfirm == null || passwordConfirm.isEmpty() || !passwordConfirm.equals(password)) {
            errors.add("Passwords must match");
        }

        if(request.getAttribute("triedOnce") == null) {
            request.setAttribute("triedOnce", "Yes");
        } else {
            errors.add("Please stop being stupid and register already");
        }

        if(errors.isEmpty()) {
            User user = User.builder().username(username)
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(password)
                    .admin(false)
                    .build();
            try {
                usersDAO.create(user);
                response.sendRedirect(request.getContextPath() + "/login");
            } catch (DuplicateKeyException e) {
                errors.add("Username already registered");
                request.setAttribute("errs", errors);
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errs", errors);
            request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
        }
    }
}
