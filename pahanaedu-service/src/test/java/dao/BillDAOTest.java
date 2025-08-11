package dao;

import models.Bill;
import models.BillItem;
import models.Customer;
import models.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BillDAOTest {

    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;

    private Customer testCustomer;
    private Item testItem;
    private Bill testBill;

    @BeforeEach
    public void setUp() {
        billDAO = new BillDAO();
        customerDAO = new CustomerDAO();
        itemDAO = new ItemDAO();

        // Create a customer
        testCustomer = new Customer();
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("test_customer@example.com");
        testCustomer.setAddress("123 Test St");
        testCustomer.setMobile("1234567890");
        testCustomer.setUnit_consumed(0);
        boolean custCreated = customerDAO.createCustomer(testCustomer);
        assertTrue(custCreated);
        assertTrue(testCustomer.getId() > 0);

        // Create an item
        testItem = new Item();
        testItem.setName("Test Item");
        testItem.setPrice(10.0);
        testItem.setQuantity(100);
        boolean itemCreated = itemDAO.createItem(testItem);
        assertTrue(itemCreated);
        assertTrue(testItem.getId() > 0);

        // Create a bill for tests
        BillItem billItem = new BillItem();
        billItem.setItem(testItem);
        billItem.setQuantity(3);
        billItem.setPrice(testItem.getPrice());
        billItem.setSubtotal(3 * testItem.getPrice());

        testBill = new Bill();
        testBill.setCustomer(testCustomer);
        testBill.setBillDate(new Date());
        testBill.setTotalAmount(billItem.getSubtotal());
        testBill.getBillItems().add(billItem);

        boolean billCreated = billDAO.createBill(testBill);
        assertTrue(billCreated);
        assertTrue(testBill.getId() > 0);
    }

    @AfterEach
    public void tearDown() {
        // TODO: Ideally add deleteBill and deleteBillItems methods for proper cleanup.
        // For now, just delete test item and customer.
       if (testBill != null && testBill.getId() > 0) {
        billDAO.deleteBill(testBill.getId());
    }
    if (testItem != null && testItem.getId() > 0) {
        itemDAO.deleteItem(testItem.getId());
    }
    if (testCustomer != null && testCustomer.getId() > 0) {
        customerDAO.deleteCustomer(testCustomer.getId());
    }
    }

    @Test
    public void testCreateBill() {
        // This is similar to setup but tests explicitly creating a new bill

        // Create customer
        Customer customer = new Customer();
        customer.setName("Bill Test Customer");
        customer.setEmail("bill@test.com");
        customer.setAddress("123 Bill St");
        customer.setMobile("9876543210");
        customer.setUnit_consumed(0);
        boolean custCreated = customerDAO.createCustomer(customer);
        assertTrue(custCreated);

        // Create item
        Item item = new Item();
        item.setName("Bill Test Item");
        item.setPrice(5.00);
        item.setQuantity(50);
        boolean itemCreated = itemDAO.createItem(item);
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

        boolean billCreated = billDAO.createBill(bill);
        assertTrue(billCreated);
        assertTrue(bill.getId() > 0);

        // Cleanup
        customerDAO.deleteCustomer(customer.getId());
        itemDAO.deleteItem(item.getId());
    }

    @Test
    public void testGetLastBillId() {
        int lastBillId = billDAO.getLastBillId();
        assertTrue(lastBillId >= testBill.getId());
    }

    @Test
    public void testGetBills() {
        List<Bill> bills = billDAO.getBills();
        assertNotNull(bills);
        assertFalse(bills.isEmpty());

        boolean found = false;
        for (Bill bill : bills) {
            if (bill.getId() == testBill.getId()) {
                found = true;
                assertEquals(testBill.getTotalAmount(), bill.getTotalAmount(), 0.001);
                assertNotNull(bill.getCustomer());
                assertEquals(testCustomer.getId(), bill.getCustomer().getId());
                assertNotNull(bill.getBillItems());
                assertFalse(bill.getBillItems().isEmpty());
                break;
            }
        }
        assertTrue(found, "Test bill should be found in getBills()");
    }

    @Test
    public void testGetBillItemsByBillId() {
        List<BillItem> billItems = billDAO.getBillItemsByBillId(testBill.getId());
        assertNotNull(billItems);
        assertFalse(billItems.isEmpty());

        for (BillItem bi : billItems) {
            assertNotNull(bi.getItem());
            assertEquals(testItem.getId(), bi.getItem().getId());
        }
    }

    @Test
    public void testGetBillById() {
        Bill fetchedBill = billDAO.getBillById(testBill.getId());
        assertNotNull(fetchedBill);
        assertEquals(testBill.getId(), fetchedBill.getId());
        assertEquals(testBill.getTotalAmount(), fetchedBill.getTotalAmount(), 0.001);

        assertNotNull(fetchedBill.getCustomer());
        assertEquals(testCustomer.getId(), fetchedBill.getCustomer().getId());

        List<BillItem> billItems = fetchedBill.getBillItems();
        assertNotNull(billItems);
        assertFalse(billItems.isEmpty());

        for (BillItem bi : billItems) {
            assertNotNull(bi.getItem());
            assertEquals(testItem.getId(), bi.getItem().getId());
        }
    }

    @Test
    public void testGetBillByIdNotFound() {
        Bill bill = billDAO.getBillById(-999); // invalid id
        assertNull(bill);
    }
}
