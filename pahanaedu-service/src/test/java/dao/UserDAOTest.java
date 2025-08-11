package dao;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class UserDAOTest {

    private UserDAO userDAO;

    private final String testUsername = "testuser";
    private final String testPassword = "testpass";

    @BeforeEach
    public void setUp() throws SQLException {
        userDAO = new UserDAO();

        // Insert a test user into DB
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO users (username, password) VALUES (?, ?)")) {
            ps.setString(1, testUsername);
            ps.setString(2, testPassword);
            ps.executeUpdate();
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Remove test user from DB
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "DELETE FROM users WHERE username = ?")) {
            ps.setString(1, testUsername);
            ps.executeUpdate();
        }
    }

    @Test
    public void testAuthenticateUser_Success() {
        boolean authenticated = userDAO.authenticateUser(testUsername, testPassword);
        assertTrue(authenticated, "User should be authenticated with correct credentials");
    }

    @Test
    public void testAuthenticateUser_WrongPassword() {
        boolean authenticated = userDAO.authenticateUser(testUsername, "wrongpass");
        assertFalse(authenticated, "Authentication should fail with wrong password");
    }

    @Test
    public void testAuthenticateUser_NonExistingUser() {
        boolean authenticated = userDAO.authenticateUser("nonexistent", "nopass");
        assertFalse(authenticated, "Authentication should fail for non-existing user");
    }
}
