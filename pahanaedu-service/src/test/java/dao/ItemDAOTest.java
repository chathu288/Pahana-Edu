package dao;

import models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    private ItemDAO itemDAO;

    @BeforeEach
    public void setUp() {
        itemDAO = new ItemDAO();
    }

    @Test
    public void testGetItems() {
        List<Item> items = itemDAO.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }

    @Test
    public void testGetItemById() {
        Item item = itemDAO.getItemById(1);
        assertNotNull(item);
        assertEquals(1, item.getId());
        assertNotNull(item.getName());
    }

    @Test
    public void testCreateUpdateDeleteItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(9.99);
        item.setQuantity(100);

        boolean created = itemDAO.createItem(item);
        assertTrue(created);
        assertTrue(item.getId() > 0);

        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(200);
        boolean updated = itemDAO.updateItem(item);
        assertTrue(updated);

        Item updatedItem = itemDAO.getItemById(item.getId());
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(19.99, updatedItem.getPrice());
        assertEquals(200, updatedItem.getQuantity());

        boolean deleted = itemDAO.deleteItem(item.getId());
        assertTrue(deleted);

        assertNull(itemDAO.getItemById(item.getId()));
    }
}
