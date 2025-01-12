package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.integration.IUsersDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    IUsersDAO usersDAO;

    @Mock
    PrintWriter responseWriter;

    ServletTest servlet;

    @BeforeEach
    public void setup() throws IOException {
        servlet = new ServletTest();
        servlet.usersDAO = usersDAO;
        when(response.getWriter()).thenReturn(responseWriter);
    }

    @Test
    void doGet() throws ServletException, IOException, DuplicateKeyException, SQLException {
        servlet.doGet(request, response);
        verify(usersDAO, atLeastOnce()).create(any());
    }
}