package dao;

import models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ItemDAOTest {

    private ItemDAO itemDAO;
    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        itemDAO = new ItemDAO();
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    public void testGetItems() throws Exception {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("name")).thenReturn("Test Item");
            when(mockResultSet.getDouble("price")).thenReturn(9.99);
            when(mockResultSet.getInt("quantity")).thenReturn(100);

            List<Item> items = itemDAO.getItems();

            assertNotNull(items);
            assertEquals(1, items.size());
            assertEquals("Test Item", items.get(0).getName());
        }
    }

    @Test
    public void testGetItemById() throws Exception {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("name")).thenReturn("Test Item");
            when(mockResultSet.getDouble("price")).thenReturn(9.99);
            when(mockResultSet.getInt("quantity")).thenReturn(100);

            Item item = itemDAO.getItemById(1);

            assertNotNull(item);
            assertEquals(1, item.getId());
            assertEquals("Test Item", item.getName());
        }
    }

    @Test
    public void testCreateItem() throws Exception {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                    .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(42);

            Item item = new Item();
            item.setName("Created Item");
            item.setPrice(19.99);
            item.setQuantity(50);

            boolean created = itemDAO.createItem(item);

            assertTrue(created);
            assertEquals(42, item.getId());
        }
    }

    @Test
    public void testUpdateItem() throws Exception {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            Item item = new Item(1, "Updated Item", 29.99, 10);
            boolean updated = itemDAO.updateItem(item);

            assertTrue(updated);
        }
    }

    @Test
    public void testDeleteItem() throws Exception {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean deleted = itemDAO.deleteItem(1);

            assertTrue(deleted);
        }
    }
}
