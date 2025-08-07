/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hd
 */
public class Utils {

    static final String DB_URL = "jdbc:mysql://localhost:3306/pahana_edu?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "Syndigi142^";

   
    
   public List<Customer> getCustomers() {
    List<Customer> customers = new ArrayList<>();
    String query = "SELECT * FROM customers";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            Customer c = new Customer();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setAddress(rs.getString("address"));
            c.setMobile(rs.getString("mobile"));
            c.setUnit_consumed(rs.getInt("unit_consumed"));
            customers.add(c);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return customers;
}

   
    public Customer getCustomerById(int id) {
    Customer c = null;
    String query = "SELECT * FROM customers WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            c = new Customer();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setAddress(rs.getString("address"));
            c.setMobile(rs.getString("mobile"));
            c.setUnit_consumed(rs.getInt("unit_consumed"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return c;
}
    
  
    public boolean createCustomer(Customer c) {
    String query = "INSERT INTO customers (name, email, address, mobile, unit_consumed) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getAddress());
        pstmt.setString(4, c.getMobile());
        pstmt.setInt(5, c.getUnit_consumed());

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
   
public boolean updateCustomer(Customer c) {
    String query = "UPDATE customers SET name = ?, email = ?, address = ?, mobile = ?, unit_consumed = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getAddress());
        pstmt.setString(4, c.getMobile());
        pstmt.setInt(5, c.getUnit_consumed());
        pstmt.setInt(6, c.getId());

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
   
    public boolean deleteCustomer(int id) {
    String query = "DELETE FROM customers WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
    public List<Item> getItems() {
    List<Item> items = new ArrayList<>();
    String query = "SELECT * FROM items";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
            items.add(item);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}

public Item getItemById(int id) {
    Item item = null;
    String query = "SELECT * FROM items WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return item;
}

public boolean createItem(Item item) {
    String query = "INSERT INTO items (name, price, quantity) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, item.getName());
        pstmt.setDouble(2, item.getPrice());
        pstmt.setInt(3, item.getQuantity());

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean updateItem(Item item) {
    String query = "UPDATE items SET name = ?, price = ?, quantity = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, item.getName());
        pstmt.setDouble(2, item.getPrice());
        pstmt.setInt(3, item.getQuantity());
        pstmt.setInt(4, item.getId());

        int rowsUpdated = pstmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean deleteItem(int id) {
    String query = "DELETE FROM items WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        int rowsDeleted = pstmt.executeUpdate();
        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean createBill(Bill bill) {
    String insertBillSQL = "INSERT INTO bills (customer_id, bill_date, total_amount) VALUES (?, ?, ?)";
    String insertBillItemSQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";
    String updateItemStockSQL = "UPDATE items SET quantity = quantity - ? WHERE id = ?";
    String updateCustomerUnitsSQL = "UPDATE customers SET unit_consumed = unit_consumed + ? WHERE id = ?";

    Connection conn = null;
    PreparedStatement billStmt = null;
    PreparedStatement billItemStmt = null;
    PreparedStatement updateStockStmt = null;
    PreparedStatement updateCustomerStmt = null;
    ResultSet generatedKeys = null;

    try {
        if (bill.getCustomer() == null || bill.getCustomer().getId() <= 0) {
            System.err.println("❌ Invalid customer data");
            return false;
        }

        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        conn.setAutoCommit(false);

        // Insert Bill
        billStmt = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);
        billStmt.setInt(1, bill.getCustomer().getId());
        billStmt.setTimestamp(2, new Timestamp(bill.getBillDate().getTime()));
        billStmt.setDouble(3, bill.getTotalAmount());

        if (billStmt.executeUpdate() == 0) {
            conn.rollback();
            return false;
        }

        generatedKeys = billStmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            conn.rollback();
            return false;
        }

        int billId = generatedKeys.getInt(1);
        bill.setId(billId);

        billItemStmt = conn.prepareStatement(insertBillItemSQL);
        updateStockStmt = conn.prepareStatement(updateItemStockSQL);
        int totalUnitsSold = 0;

        for (BillItem item : bill.getBillItems()) {
            if (item == null || item.getItem() == null || item.getItem().getId() <= 0) {
                System.err.println("❌ Invalid bill item or item ID");
                conn.rollback();
                return false;
            }

            System.out.println("✅ Processing item ID: " + item.getItem().getId() + " Qty: " + item.getQuantity());

            // Insert into bill_items
            billItemStmt.setInt(1, billId);
            billItemStmt.setInt(2, item.getItem().getId());
            billItemStmt.setInt(3, item.getQuantity());
            billItemStmt.setDouble(4, item.getPrice());
            billItemStmt.setDouble(5, item.getSubtotal());
            billItemStmt.addBatch();

            // Update stock
            updateStockStmt.setInt(1, item.getQuantity());
            updateStockStmt.setInt(2, item.getItem().getId());
            updateStockStmt.addBatch();

            totalUnitsSold += item.getQuantity();
        }

        // Execute batch inserts and stock updates
        for (int result : billItemStmt.executeBatch()) {
            if (result == Statement.EXECUTE_FAILED) {
                System.err.println("❌ Failed inserting bill item");
                conn.rollback();
                return false;
            }
        }
        for (int result : updateStockStmt.executeBatch()) {
            if (result == Statement.EXECUTE_FAILED) {
                System.err.println("❌ Failed updating stock");
                conn.rollback();
                return false;
            }
        }

        // Update customer unit_consumed
        updateCustomerStmt = conn.prepareStatement(updateCustomerUnitsSQL);
        updateCustomerStmt.setInt(1, totalUnitsSold);
        updateCustomerStmt.setInt(2, bill.getCustomer().getId());

        int updateRes = updateCustomerStmt.executeUpdate();
        if (updateRes == 0) {
            System.err.println("❌ Failed updating customer units");
            conn.rollback();
            return false;
        }

        conn.commit();
        System.out.println("✅ Bill creation successful");
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    } finally {
        try {
            if (generatedKeys != null) generatedKeys.close();
            if (billStmt != null) billStmt.close();
            if (billItemStmt != null) billItemStmt.close();
            if (updateStockStmt != null) updateStockStmt.close();
            if (updateCustomerStmt != null) updateCustomerStmt.close();
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public boolean authenticateUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // true if user found
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
  public int getLastBillId() {
        String query = "SELECT MAX(id) AS maxId FROM bills";
        int lastId = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("maxId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }
public List<Bill> getBills() {
    List<Bill> bills = new ArrayList<>();

    String sqlBills = "SELECT b.id, b.bill_date, b.total_amount, " +
                      "c.id as cust_id, c.name as cust_name, c.email, c.address, c.mobile " +
                      "FROM bills b JOIN customers c ON b.customer_id = c.id ORDER BY b.id DESC";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(sqlBills);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Bill bill = new Bill();
            bill.setId(rs.getInt("id"));
            bill.setBillDate(rs.getTimestamp("bill_date"));
            bill.setTotalAmount(rs.getDouble("total_amount"));

            // Customer info
            Customer customer = new Customer();
            customer.setId(rs.getInt("cust_id"));
            customer.setName(rs.getString("cust_name"));
            customer.setEmail(rs.getString("email"));
            customer.setAddress(rs.getString("address"));
            customer.setMobile(rs.getString("mobile"));

            bill.setCustomer(customer);

            // Now load bill items for this bill:
            bill.setBillItems(getBillItemsByBillId(bill.getId()));

            bills.add(bill);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bills;
}

public List<BillItem> getBillItemsByBillId(int billId) {
    List<BillItem> billItems = new ArrayList<>();
    String sqlBillItems = "SELECT bi.id, bi.quantity, bi.price, bi.subtotal, " +
                          "i.id as item_id, i.name as item_name, i.price as item_price " +
                          "FROM bill_items bi JOIN items i ON bi.item_id = i.id WHERE bi.bill_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(sqlBillItems)) {

        stmt.setInt(1, billId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            BillItem bi = new BillItem();
            bi.setId(rs.getInt("id"));
            bi.setQuantity(rs.getInt("quantity"));
            bi.setPrice(rs.getDouble("price"));
            bi.setSubtotal(rs.getDouble("subtotal"));

            Item item = new Item();
            item.setId(rs.getInt("item_id"));
            item.setName(rs.getString("item_name"));
            item.setPrice(rs.getDouble("item_price"));

            bi.setItem(item);

            billItems.add(bi);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return billItems;
}
public Bill getBillById(int id) {
    Bill bill = null;

    String sqlBill = "SELECT b.id, b.bill_date, b.total_amount, " +
                     "c.id as customer_id, c.name, c.email, c.address, c.mobile " +
                     "FROM bills b " +
                     "JOIN customers c ON b.customer_id = c.id " +
                     "WHERE b.id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(sqlBill)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            bill = new Bill();
            bill.setId(rs.getInt("id"));
            bill.setBillDate(rs.getTimestamp("bill_date"));
            bill.setTotalAmount(rs.getDouble("total_amount"));

            Customer customer = new Customer();
            customer.setId(rs.getInt("customer_id"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customer.setAddress(rs.getString("address"));
            customer.setMobile(rs.getString("mobile"));
            bill.setCustomer(customer);

            // Get bill items using existing method
            List<BillItem> items = getBillItemsByBillId(id);
            bill.setBillItems(items);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return bill;
}
}
