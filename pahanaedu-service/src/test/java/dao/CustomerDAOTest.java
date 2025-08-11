package dao;

import models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;

    @BeforeEach
    public void setUp() {
        customerDAO = new CustomerDAO();
    }

    @Test
    public void testGetCustomers() {
        List<Customer> customers = customerDAO.getCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = customerDAO.getCustomerById(1);
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

        boolean created = customerDAO.createCustomer(customer);
        assertTrue(created);
        assertTrue(customer.getId() > 0);

        customer.setName("Updated Customer");
        customer.setUnit_consumed(10);
        boolean updated = customerDAO.updateCustomer(customer);
        assertTrue(updated);

        Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals("Updated Customer", updatedCustomer.getName());
        assertEquals(10, updatedCustomer.getUnit_consumed());

        boolean deleted = customerDAO.deleteCustomer(customer.getId());
        assertTrue(deleted);

        assertNull(customerDAO.getCustomerById(customer.getId()));
    }
}
