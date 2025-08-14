package dao;

import models.Customer;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DBConnection> mockedDB;

    @BeforeEach
    public void setUp() throws Exception {
        customerDAO = new CustomerDAO();

        // Mock JDBC components
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock DBConnection.getConnection()
        mockedDB = mockStatic(DBConnection.class);
        mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    public void tearDown() {
        mockedDB.close();
    }

    @Test
    public void testGetCustomers() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate one row in ResultSet
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("address")).thenReturn("123 Street");
        when(mockResultSet.getString("mobile")).thenReturn("1234567890");
        when(mockResultSet.getInt("unit_consumed")).thenReturn(5);

        List<Customer> customers = customerDAO.getCustomers();

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Jane Doe");
        when(mockResultSet.getString("email")).thenReturn("jane@example.com");
        when(mockResultSet.getString("address")).thenReturn("456 Avenue");
        when(mockResultSet.getString("mobile")).thenReturn("9876543210");
        when(mockResultSet.getInt("unit_consumed")).thenReturn(10);

        Customer customer = customerDAO.getCustomerById(1);

        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("Jane Doe", customer.getName());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer(0, "Test User", "test@example.com", "789 Road", "5555555555", 0);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ResultSet mockKeys = mock(ResultSet.class);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockKeys);
        when(mockKeys.next()).thenReturn(true);
        when(mockKeys.getInt(1)).thenReturn(99);

        boolean created = customerDAO.createCustomer(newCustomer);

        assertTrue(created);
        assertEquals(99, newCustomer.getId());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existing = new Customer(1, "Old Name", "old@example.com", "Addr", "000", 1);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean updated = customerDAO.updateCustomer(existing);

        assertTrue(updated);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean deleted = customerDAO.deleteCustomer(1);

        assertTrue(deleted);
    }
}
