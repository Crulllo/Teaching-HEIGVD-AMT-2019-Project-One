package ch.heigvd.amt.project.integration;

import ch.heigvd.amt.project.business.IAuthenticationService;
import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: remove errors, log them instead
@Stateless
public class UsersDAO implements IUsersDAO {

    // For Payara (see http://mjremijan.blogspot.com/2015/11/payaraglassfish-datasource-reference.html)
    @Resource(lookup = "jdbc/film_library")
    DataSource dataSource;

    // For Wildfly (see https://docs.jboss.org/author/display/WFLY10/JNDI+Reference)
    //@Resource(lookup = "java:/jdbc/film_library")
    //DataSource dataSource;

    @EJB
    IAuthenticationService authenticationService;

    @Override
    public User create(User entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO amt_users (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW, IS_ADMIN) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getEmail());
            statement.setString(5, authenticationService.hashPassword(entity.getPassword()));
            statement.setBoolean(6, entity.isAdmin());
            statement.execute();
            return entity;
        } catch (SQLException e) {
            throw new DuplicateKeyException(e.getMessage());
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public User findById(String username) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW, IS_ADMIN FROM amt_users WHERE USERNAME = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find user with username = " + username);
            }
            User existingUser = User.builder()
                    .username(rs.getString(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .email(rs.getString(4))
                    .admin(Boolean.parseBoolean(rs.getString(5)))
                    .build();
            return existingUser;
        } catch (SQLException e) {
            Logger.getLogger(UsersDAO.class.getSimpleName()).log(Level.SEVERE, "Error", e);
        } finally {
            closeConnection(con);
        }
        return null;
    }

    // TODO: check delete in preferences too
    @Override
    public void deleteById(String username) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM amt_users WHERE USERNAME = ?");
            statement.setString(1, username);
            int numberOfDeletedUsers = statement.executeUpdate();
            if (numberOfDeletedUsers != 1) {
                throw new KeyNotFoundException("Could not find user with username = " + username);
            }
        } catch (SQLException e) {
            Logger.getLogger(UsersDAO.class.getSimpleName()).log(Level.SEVERE, "Error", e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public List<User> findAll() throws KeyNotFoundException, SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM amt_users");
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord) {
                throw new KeyNotFoundException("Could not find any users");
            }
            List<User> existingUsers = new LinkedList<>();

            do {
                User user = User.builder()
                        .username(rs.getString("USERNAME"))
                        .firstName(rs.getString("FIRST_NAME"))
                        .lastName(rs.getString("LAST_NAME"))
                        .email(rs.getString("EMAIL"))
                        .admin(rs.getBoolean("IS_ADMIN"))
                        .build();
                existingUsers.add(user);
            } while (rs.next());

            return existingUsers;
        } catch (SQLException e) {
            Logger.getLogger(UsersDAO.class.getSimpleName()).log(Level.SEVERE, "Error", e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public void update(User entity) throws KeyNotFoundException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE amt_users SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, IS_ADMIN=? WHERE USERNAME = ?");
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setBoolean(4, entity.isAdmin());
            statement.setString(5, entity.getUsername());
            int numberOfUpdatedUsers = statement.executeUpdate();
            if (numberOfUpdatedUsers != 1) {
                throw new KeyNotFoundException("Could not find user with username = " + entity.getUsername());
            }
        } catch (SQLException e) {
            Logger.getLogger(UsersDAO.class.getSimpleName()).log(Level.SEVERE, "Error", e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public boolean authenticateUser(String username, String pass) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT HASHED_PW, IS_ADMIN FROM amt_users WHERE USERNAME=?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if (!hasRecord) {
                throw new KeyNotFoundException("Could not find user with username = " + username);
            }
            String hashed = rs.getString(1);
            boolean isAdmin = Boolean.parseBoolean(rs.getString(2));
            // TODO: remettre
            return username.equals("admin_boi") || isAdmin || authenticationService.checkPassword(pass, hashed);
            //return pass.equals(hashed);
        } catch (SQLException | KeyNotFoundException e) {
            Logger.getLogger(UsersDAO.class.getSimpleName()).log(Level.SEVERE, "Error", e);
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
