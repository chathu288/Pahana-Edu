/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package utils;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hd
 */
public class UtilsTest {
    
     private Utils utils;

    @BeforeEach
    public void setUp() {
        utils = new Utils();
    }

    @Test
    public void testGetStudents() {
        List<Student> students = utils.getStudents();
        assertNotNull(students);
        assertTrue(students.size() >= 0); // At least no error and returns a list
    }

    @Test
    public void testGetStudentById() {
        Student student = utils.getStudentById(1);
        assertNotNull(student);
        assertEquals(1, student.getId());
        assertNotNull(student.getName());
    }

    @Test
    public void testCreateUpdateDeleteStudent() {
        // Create
        Student student = new Student();
        student.setName("Test Student");
        boolean created = utils.createStudent(student);
        assertTrue(created);
        assertTrue(student.getId() > 0);

        // Update
        student.setName("Updated Test Student");
        boolean updated = utils.updateStudent(student);
        assertTrue(updated);

        // Get by ID and verify updated name
        Student updatedStudent = utils.getStudentById(student.getId());
        assertEquals("Updated Test Student", updatedStudent.getName());

        // Delete
        boolean deleted = utils.deleteStudent(student.getId());
        assertTrue(deleted);

        // Verify deletion
        assertNull(utils.getStudentById(student.getId()));
    }

    // Customer tests
    @Test
    public void testGetCustomers() {
        List<Customer> customers = utils.getCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = utils.getCustomerById(1);
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertNotNull(customer.getName());
    }

    @Test
    public void testCreateUpdateDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setAddress("123 Test St");
        customer.setMobile("1234567890");
        customer.setUnit_consumed(0);

        boolean created = utils.createCustomer(customer);
        assertTrue(created);
        assertTrue(customer.getId() > 0);

        customer.setName("Updated Customer");
        customer.setUnit_consumed(10);
        boolean updated = utils.updateCustomer(customer);
        assertTrue(updated);

        Customer updatedCustomer = utils.getCustomerById(customer.getId());
        assertEquals("Updated Customer", updatedCustomer.getName());
        assertEquals(10, updatedCustomer.getUnit_consumed());

        boolean deleted = utils.deleteCustomer(customer.getId());
        assertTrue(deleted);

        assertNull(utils.getCustomerById(customer.getId()));
    }

    // Item tests
    @Test
    public void testGetItems() {
        List<Item> items = utils.getItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }

    @Test
    public void testGetItemById() {
        Item item = utils.getItemById(1);
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

        boolean created = utils.createItem(item);
        assertTrue(created);
        assertTrue(item.getId() > 0);

        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(200);
        boolean updated = utils.updateItem(item);
        assertTrue(updated);

        Item updatedItem = utils.getItemById(item.getId());
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals(19.99, updatedItem.getPrice());
        assertEquals(200, updatedItem.getQuantity());

        boolean deleted = utils.deleteItem(item.getId());
        assertTrue(deleted);

        assertNull(utils.getItemById(item.getId()));
    }

    // Bill test (basic)
    @Test
    public void testCreateBill() {
        // First create a customer for the bill
        Customer customer = new Customer();
        customer.setName("Bill Test Customer");
        customer.setEmail("bill@test.com");
        customer.setAddress("123 Bill St");
        customer.setMobile("9876543210");
        customer.setUnit_consumed(0);
        boolean custCreated = utils.createCustomer(customer);
        assertTrue(custCreated);

        // Create an item for the bill
        Item item = new Item();
        item.setName("Bill Test Item");
        item.setPrice(5.00);
        item.setQuantity(50);
        boolean itemCreated = utils.createItem(item);
        assertTrue(itemCreated);

        // Create bill item
        BillItem billItem = new BillItem();
        billItem.setItem(item);
        billItem.setQuantity(2);
        billItem.setPrice(item.getPrice());
        billItem.setSubtotal(item.getPrice() * 2);

        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setBillDate(new Date());
        bill.setTotalAmount(billItem.getSubtotal());
        bill.getBillItems().add(billItem);

        boolean billCreated = utils.createBill(bill);
        assertTrue(billCreated);
        assertTrue(bill.getId() > 0);

        // Cleanup - delete created customer and item
        utils.deleteCustomer(customer.getId());
        utils.deleteItem(item.getId());
    }
}
