package dao;

import models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    private ItemDAO itemDAO;

    private Item testItem; // to store created item for update/delete tests

    @BeforeEach
    public void setUp() {
        itemDAO = new ItemDAO();
        testItem = null;
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
    public void testCreateItem() {
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(9.99);
        item.setQuantity(100);

        boolean created = itemDAO.createItem(item);
        assertTrue(created);
        assertTrue(item.getId() > 0);

        // Save created item for further use
        testItem = item;
    }

    @Test
    public void testUpdateItem() {
        // First create an item to update
        Item item = new Item();
        item.setName("Item To Update");
        item.setPrice(5.00);
        item.setQuantity(50);
        boolean created = itemDAO.createItem(item);
        assertTrue(created);

        // Update the item fields
        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(200);

        boolean updated = itemDAO.updateItem(item);
        assertTrue(updated);

        // Verify updated values
        Item updatedItem = itemDAO.getItemById(item.getId());
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(19.99, updatedItem.getPrice());
        assertEquals(200, updatedItem.getQuantity());

        // Clean up
        itemDAO.deleteItem(item.getId());
    }

    @Test
    public void testDeleteItem() {
        // First create an item to delete
        Item item = new Item();
        item.setName("Item To Delete");
        item.setPrice(10.00);
        item.setQuantity(10);
        boolean created = itemDAO.createItem(item);
        assertTrue(created);

        // Delete the item
        boolean deleted = itemDAO.deleteItem(item.getId());
        assertTrue(deleted);

        // Verify item no longer exists
        assertNull(itemDAO.getItemById(item.getId()));
    }
}
