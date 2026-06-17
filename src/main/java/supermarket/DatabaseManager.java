/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Edwin
 */
public class DatabaseManager {
    // Database connection credentials
    private String url = "jdbc:mysql://localhost:3306/supermarket_db";
    private String user = "root";
    private String password = "";
    private Connection conn;

    // Constructor establishes the connection
    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected securely.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UC1: Add Category
    public boolean addCategory(String name) {
        String query = "INSERT INTO Category_record (category_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false; 
        }
    }

    // UC2: Add Product (UPGRADED FOR OOP FACTORY PATTERN)
    public boolean addProduct(Product p) {
        String query = "INSERT INTO Product_record (product_name, price, stock_quantity, categoryID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // We now pull the data directly out of the Product object using your getters!
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getStockQuantity());
            stmt.setInt(4, p.getCategoryID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false; 
        }
    }

    // UC3: Update Product Details (UPGRADED FOR OOP)
    public boolean updateProduct(Product p) {
        String query = "UPDATE Product_record SET product_name = ?, price = ?, stock_quantity = ?, categoryID = ? WHERE productID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getProductName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getStockQuantity());
            stmt.setInt(4, p.getCategoryID());
            
            // The WHERE clause needs the ID so it knows WHICH row to update!
            stmt.setInt(5, p.getProductID()); 
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false; 
        }
    }

    // UC4: Remove Product
    public boolean removeProduct(int productId) {
        String query = "DELETE FROM Product_record WHERE productID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false; 
        }
    }

    // UC5: Track Stock Levels (UPGRADED FOR DATA STRUCTURES & COLLECTIONS)
    public List<Product> getAllProducts() {
        // 1. Create our Data Structure (ArrayList)
        List<Product> productList = new ArrayList<>();
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT productID, product_name, price, stock_quantity, categoryID FROM Product_record");
            
            while (rs.next()) {
                // 2. Extract data from the database row
                int id = rs.getInt("productID");
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock_quantity");
                int catId = rs.getInt("categoryID");
                
                // 3. Use your Factory's Machine 2 to rebuild the Object!
                Product p = ProductFactory.buildExistingProduct(id, name, price, stock, catId);
                
                // 4. Add the Object to our Collection
                productList.add(p);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        
        // 5. Return the fully populated List
        return productList;
    }

  // --- SUPPLIER CRUD OPERATIONS ---

    // 1. CREATE: Add a new supplier
    public boolean addSupplier(String name, String contactInfo) {
        String query = "INSERT INTO Supplier_record (supplier_name, contact_info) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, contactInfo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. READ: Fetch all suppliers for the table
    public java.util.List<Object[]> getAllSuppliers() {
        java.util.List<Object[]> supplierList = new java.util.ArrayList<>();
        String query = "SELECT supplierID, supplier_name, contact_info FROM Supplier_record";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                supplierList.add(new Object[]{
                    rs.getInt("supplierID"), 
                    rs.getString("supplier_name"),
                    rs.getString("contact_info")
                });
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return supplierList;
    }

    // 3. UPDATE: Change an existing supplier's details
    public boolean updateSupplier(int id, String name, String contactInfo) {
        String query = "UPDATE Supplier_record SET supplier_name = ?, contact_info = ? WHERE supplierID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, contactInfo);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    // 4. DELETE: Remove a supplier
    public boolean deleteSupplier(int id) {
        String query = "DELETE FROM Supplier_record WHERE supplierID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
    
    // UC7: Request Restock
    public boolean requestRestock(int productId, int additionalQuantity) {
        String query = "UPDATE Product_record SET stock_quantity = stock_quantity + ? WHERE productID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, additionalQuantity);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false; 
        }
    }
    
    // --- CATEGORY CRUD OPERATIONS ---

    // 1. READ: Fetch all categories for the table
    public java.util.List<Object[]> getAllCategories() {
        java.util.List<Object[]> categoryList = new java.util.ArrayList<>();
        String query = "SELECT categoryID, category_name FROM Category_record";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                categoryList.add(new Object[]{
                    rs.getInt("categoryID"), 
                    rs.getString("category_name")
                });
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return categoryList;
    }

    // 2. UPDATE: Change an existing category name
    public boolean updateCategory(int id, String newName) {
        String query = "UPDATE Category_record SET category_name = ? WHERE categoryID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    // 3. DELETE: Remove a category
    public boolean deleteCategory(int id) {
        String query = "DELETE FROM Category_record WHERE categoryID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
    
    // --- MODULE 4: SALES INTEGRATION ---
    public boolean deductStock(int productId, int quantityToBuy) {
        try {
            // The "AND stock_quantity >= ?" prevents the system from selling items you don't have!
            String sql = "UPDATE Product_record SET stock_quantity = stock_quantity - ? WHERE productID = ? AND stock_quantity >= ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, quantityToBuy);
            pst.setInt(2, productId);
            pst.setInt(3, quantityToBuy);
            
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0; // Returns true if stock was successfully reduced
        } catch (Exception e) {
            System.out.println("Database Error during sale: " + e.getMessage());
            return false;
        }
    }
    
// --- MODULE: ADVANCED CART CHECKOUT ---
    public boolean processCartCheckout(java.util.List<Object[]> cartData) {
        try {
            conn.setAutoCommit(false); 

            // Generate a unique receipt number for this customer's transaction
            String receiptNo = "REC-" + System.currentTimeMillis();

            // SQL 1: Deduct from Supermarket Inventory
            String updateStockSql = "UPDATE Product_record SET stock_quantity = stock_quantity - ? WHERE productID = ?";
            java.sql.PreparedStatement pstStock = conn.prepareStatement(updateStockSql);

            // SQL 2: Save to Sales Record (Now includes ALL required columns from your screenshot!)
            String insertSaleSql = "INSERT INTO sales_record (receipt_no, product_id, product_name, quantity, subtotal, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pstSale = conn.prepareStatement(insertSaleSql);

            for (Object[] item : cartData) {
                int productId = (int) item[0];
                String productName = (String) item[1];
                int qty = (int) item[3];
                double subTotal = (double) item[4]; 

                // 1. Execute Stock Deduction
                pstStock.setInt(1, qty);
                pstStock.setInt(2, productId);
                pstStock.executeUpdate();

                // 2. Execute Sales Record Insertion
                pstSale.setString(1, receiptNo);
                pstSale.setInt(2, productId);
                pstSale.setString(3, productName);
                pstSale.setInt(4, qty);
                pstSale.setDouble(5, subTotal);     // Fills your 'subtotal' column
                pstSale.setDouble(6, subTotal);     // Fills your 'total_price' column
                pstSale.executeUpdate();
            }

            conn.commit(); 
            return true;
            
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {} 
            System.out.println("Checkout DB Error: " + e.getMessage());
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {} 
        }
    }
    
    // --- MODULE 2: SUPPLIER MANAGEMENT ---
    public boolean registerSupplier(String supplierID, String password) {
        try {
            String sql = "INSERT INTO Supplier_record (supplier_id, password) VALUES (?, ?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, supplierID);
            pst.setString(2, password);
            
            int rowsInserted = pst.executeUpdate();
            return rowsInserted > 0; // Returns true if saved successfully
        } catch (Exception e) {
            System.out.println("Database Error (Register Supplier): " + e.getMessage());
            return false;
        }
    }
    
    // --- MODULE 2: SUPPLIER LOGIN VALIDATION ---
    public boolean verifySupplierLogin(String supplierID, String password) {
        try {
            // This SQL query asks: "Is there a row with this exact ID and Password?"
            String sql = "SELECT * FROM Supplier_record WHERE supplier_id = ? AND password = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, supplierID);
            pst.setString(2, password);
            
            java.sql.ResultSet rs = pst.executeQuery();
            
            // If rs.next() is true, it found a match! If false, the login fails.
            return rs.next(); 
            
        } catch (Exception e) {
            System.out.println("Database Error (Login): " + e.getMessage());
            return false;
        }
    }
    
    // --- MODULE: PRODUCT MANAGEMENT (CRUD) ---
    
    // 1. CREATE
    public boolean addProduct(int id, String name, double price, int stock) {
        try {
            String sql = "INSERT INTO Product_record (productID, productName, price, stock_quantity) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setDouble(3, price);
            pst.setInt(4, stock);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Add Product): " + e.getMessage());
            return false;
        }
    }

    // 2. UPDATE
    public boolean updateProduct(int id, String name, double price, int stock) {
        try {
            String sql = "UPDATE Product_record SET productName = ?, price = ?, stock_quantity = ? WHERE productID = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, stock);
            pst.setInt(4, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Update Product): " + e.getMessage());
            return false;
        }
    }

    // 3. DELETE
    public boolean deleteProduct(int id) {
        try {
            String sql = "DELETE FROM Product_record WHERE productID = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Delete Product): " + e.getMessage());
            return false;
        }
    }

    // 4. AUTO-GENERATE ID (Finds the highest ID in the database and adds 1)
    public int generateNextProductID() {
        try {
            String sql = "SELECT MAX(productID) AS max_id FROM Product_record";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId > 0 ? maxId + 1 : 101; // Starts at 101 if table is empty
            }
        } catch (Exception e) {
            System.out.println("DB Error (Generate ID): " + e.getMessage());
        }
        return 101; 
    }
    
    // 1. CREATE (Upgraded with Cost & Status)
    public boolean addProduct(int id, String name, double price, int stock, double supplierPrice, String status) {
        try {
            String sql = "INSERT INTO Product_record (productID, product_name, price, stock_quantity, supplier_price, status) VALUES (?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setDouble(3, price);
            pst.setInt(4, stock);
            pst.setDouble(5, supplierPrice);
            pst.setString(6, status);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Add Product): " + e.getMessage());
            return false;
        }
    }

    // 2. UPDATE (Upgraded with Cost & Status)
    public boolean updateProduct(int id, String name, double price, int stock, double supplierPrice, String status) {
        try {
            String sql = "UPDATE Product_record SET productName = ?, price = ?, stock_quantity = ?, supplier_price = ?, status = ? WHERE productID = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, stock);
            pst.setDouble(4, supplierPrice);
            pst.setString(5, status);
            pst.setInt(6, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Update Product): " + e.getMessage());
            return false;
        }
    }
    
    // --- MODULE: FETCH RAW PRODUCT DATA FOR CATALOG ---
 public java.util.List<String[]> getRawProductData() {
        java.util.List<String[]> stringData = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM Product_record";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                // IMPORTANT: Use the exact names from your phpMyAdmin 'Structure' tab
                stringData.add(new String[]{
                    String.valueOf(rs.getInt("productID")),
                    rs.getString("product_name"), // Try 'product_name' or 'productName'
                    String.valueOf(rs.getDouble("price")),
                    String.valueOf(rs.getInt("stock_quantity")),
                    String.valueOf(rs.getDouble("supplier_price")),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            System.out.println("DB Error (Fetch Catalog): " + e.getMessage());
        }
        return stringData;
    }
    
    // --- MODULE 2: SECURE SUPPLIER PRODUCT ACTIONS ---
    
    // 1. Supplier Adds Product (Forces Retail Price to 0.00 automatically)
    public boolean addSupplierProduct(int id, String name, int stock, double supplierPrice, String status) {
        try {
            // Notice: 'price' is hardcoded to 0.00 in the SQL statement!
            String sql = "INSERT INTO Product_record (productID, product_name, price, stock_quantity, supplier_price, status) VALUES (?, ?, 0.00, ?, ?, ?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, stock);
            pst.setDouble(4, supplierPrice);
            pst.setString(5, status);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Supplier Add): " + e.getMessage());
            return false;
        }
    }

    // 2. Supplier Updates Product (Safely ignores the Retail Price column)
    public boolean updateSupplierProduct(int id, String name, int stock, double supplierPrice, String status) {
        try {
            // Notice: We do NOT update the 'price' column here! The Admin's retail price is safe.
            String sql = "UPDATE Product_record SET product_name = ?, stock_quantity = ?, supplier_price = ?, status = ? WHERE productID = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, stock);
            pst.setDouble(3, supplierPrice);
            pst.setString(4, status);
            pst.setInt(5, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("DB Error (Supplier Update): " + e.getMessage());
            return false;
        }
    }
    
    // --- MODULE 2: PURCHASE ORDER HANDLING ---
    public boolean processPurchaseOrder(String supplierId, int productId, String productName, int qty, double supplierPrice) {
        try {
            // Start Transaction: Both actions must succeed, or neither does
            conn.setAutoCommit(false); 
            
            // 1. INCREASE Supermarket Stock
            String updateSql = "UPDATE Product_record SET stock_quantity = stock_quantity + ? WHERE productID = ?";
            java.sql.PreparedStatement pstUpdate = conn.prepareStatement(updateSql);
            pstUpdate.setInt(1, qty);
            pstUpdate.setInt(2, productId);
            pstUpdate.executeUpdate();
            
            // 2. Log the Purchase Order for the Supplier History Module
            double totalCost = qty * supplierPrice;
            String insertSql = "INSERT INTO Purchase_Order_record (supplier_id, product_id, product_name, quantity, total_cost) VALUES (?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pstInsert = conn.prepareStatement(insertSql);
            pstInsert.setString(1, supplierId);
            pstInsert.setInt(2, productId);
            pstInsert.setString(3, productName);
            pstInsert.setInt(4, qty);
            pstInsert.setDouble(5, totalCost);
            pstInsert.executeUpdate();
            
            // Save all changes permanently
            conn.commit(); 
            return true;
            
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {} // Failsafe undo
            System.out.println("DB Error (Purchase Order): " + e.getMessage());
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {} 
        }
    }
    
    // --- MODULE 2: SUPPLIER HISTORY REPORTING ---
    public java.util.List<String[]> getSupplierHistory(String searchId) {
        java.util.List<String[]> historyList = new java.util.ArrayList<>();
        try {
            String sql;
            java.sql.PreparedStatement pst;
            
            if (searchId == null || searchId.isEmpty()) {
                sql = "SELECT * FROM Purchase_Order_record ORDER BY order_date DESC";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM Purchase_Order_record WHERE supplier_id = ? ORDER BY order_date DESC";
                pst = conn.prepareStatement(sql);
                pst.setString(1, searchId);
            }
            
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                historyList.add(new String[]{
                    String.valueOf(rs.getInt("po_id")),
                    rs.getString("supplier_id"),
                    rs.getString("product_name"),
                    rs.getString("order_date"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            System.out.println("DB Error (History): " + e.getMessage());
        }
        return historyList;
    }
    
    // --- MODULE: SALES REPORTING ---
    public String generateSalesReportData() {
        StringBuilder report = new StringBuilder();
        report.append("--- SALES REPORT ---\n");
        report.append("Date: " + java.time.LocalDate.now() + "\n\n");
        report.append(String.format("%-15s %-10s %-10s\n", "Product", "Qty", "Total"));
        
        try {
            // Assuming you have a 'Sales_record' table
            String sql = "SELECT productName, SUM(quantity) as totalQty, SUM(totalPrice) as revenue FROM Sales_record GROUP BY productName";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                report.append(String.format("%-15s %-10d %-10.2f\n", 
                    rs.getString("productName"), rs.getInt("totalQty"), rs.getDouble("revenue")));
            }
        } catch (Exception e) {
            return "Error generating report: " + e.getMessage();
        }
        return report.toString();
    }
    
  // --- MODULE: SALES REPORTING ---
 public String getLatestSalesReport() {
        StringBuilder report = new StringBuilder();
        report.append("=================== LATEST SALES REPORT ===================\n");
        report.append(String.format("%-25s | %-10s | %-15s\n", "Product Name", "Total Qty", "Total Revenue"));
        report.append("-----------------------------------------------------------\n");

        try {
            // FIXED: Using product_name and total_price (with underscores!)
            String sql = "SELECT product_name, SUM(quantity) as totalQty, SUM(total_price) as revenue FROM Sales_record GROUP BY product_name";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                report.append(String.format("%-25s | %-10d | RM %.2f\n", 
                    rs.getString("product_name"), // FIXED
                    rs.getInt("totalQty"), 
                    rs.getDouble("revenue")));
            }
        } catch (Exception e) {
            return "Database Error: " + e.getMessage();
        }
        return report.toString();
    }
    
    // --- UPGRADED CHECKOUT SAVE METHOD ---
    public boolean recordSale(String product_name, int quantity, double unitPrice) {
        try {
            // Calculate the total price right here in Java!
            double totalPrice = quantity * unitPrice;

            // Save BOTH the quantity and the total money to the database
            String sql = "INSERT INTO Sales_record (product_name, quantity, total_price) VALUES (?, ?, ?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, product_name);
            pst.setInt(2, quantity);
            pst.setDouble(3, totalPrice); // Save the calculated revenue
            
            return pst.executeUpdate() > 0;
            
        } catch (Exception e) {
            System.out.println("DB Error (Checkout Save): " + e.getMessage());
            return false;
        }
    }
    
    // --- MODULE: PROFIT ANALYSIS ---
    public double[] getProfitAnalysisData() {
        // We will return an array holding: [0]=Revenue, [1]=Expenses, [2]=Net Profit, [3]=Margin %
        double[] financials = new double[4]; 
        
        try {
            // This SQL query joins your sales table with your product table to calculate the exact cost
            String sql = "SELECT " +
                         "SUM(s.total_price) AS totalRevenue, " +
                         "SUM(s.quantity * p.supplier_price) AS totalCost " +
                         "FROM sales_record s " +
                         "JOIN product_record p ON s.product_id = p.productID";
                         
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                financials[0] = rs.getDouble("totalRevenue");
                financials[1] = rs.getDouble("totalCost");
                financials[2] = financials[0] - financials[1]; // Revenue minus Cost = Net Profit
                
                // Calculate margin percentage (safeguard against dividing by zero)
                if (financials[0] > 0) {
                    financials[3] = (financials[2] / financials[0]) * 100;
                } else {
                    financials[3] = 0.0;
                }
            }
        } catch (Exception e) {
            System.out.println("Profit Analysis DB Error: " + e.getMessage());
        }
        
        return financials;
    }
    
    // --- MODULE: THRESHOLD CONFIGURATION ---
    public boolean updateLowStockThreshold(boolean isCategoryUpdate, int targetId, int newThreshold) {
        try {
            String sql;
            // Alternate Flow: Update by Category ID
            if (isCategoryUpdate) {
                sql = "UPDATE Product_record SET min_threshold = ? WHERE categoryID = ?";
            } 
            // Basic Flow: Update by Single Product ID
            else {
                sql = "UPDATE Product_record SET min_threshold = ? WHERE productID = ?";
            }
            
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, newThreshold);
            pst.setInt(2, targetId);
            
            return pst.executeUpdate() > 0;
            
        } catch (Exception e) {
            System.out.println("Threshold DB Error: " + e.getMessage());
            return false;
        }
    }
}
