package dao;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;

    private MockedStatic<DBConnection> mockedStaticConnection;

    private UserDAO userDAO;

    private final String testUsername = "testuser";
    private final String testPassword = "testpass";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock static DBConnection.getConnection()
        mockedStaticConnection = mockStatic(DBConnection.class);
        mockedStaticConnection.when(DBConnection::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        userDAO = new UserDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedStaticConnection.close();
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        when(mockResultSet.next()).thenReturn(true);

        boolean authenticated = userDAO.authenticateUser(testUsername, testPassword);

        assertTrue(authenticated);
        verify(mockPreparedStatement).setString(1, testUsername);
        verify(mockPreparedStatement).setString(2, testPassword);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testAuthenticateUser_WrongPassword() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        boolean authenticated = userDAO.authenticateUser(testUsername, "wrongpass");

        assertFalse(authenticated);
        verify(mockPreparedStatement).setString(1, testUsername);
        verify(mockPreparedStatement).setString(2, "wrongpass");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testAuthenticateUser_NonExistingUser() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        boolean authenticated = userDAO.authenticateUser("nonexistent", "nopass");

        assertFalse(authenticated);
        verify(mockPreparedStatement).setString(1, "nonexistent");
        verify(mockPreparedStatement).setString(2, "nopass");
        verify(mockPreparedStatement).executeQuery();
    }
}
