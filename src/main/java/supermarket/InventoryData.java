/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class InventoryData {
    private static final String URL = "jdbc:mysql://localhost:3306/smart_inventory_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public List<StockItem> getStockLevels() {
        List<StockItem> databaseItems = new ArrayList<>();

        try {
            // Force the driver to load
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stock_items");

            while (rs.next()) {
                databaseItems.add(new StockItem(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getInt("threshold")
                ));
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("CRITICAL ERROR: MySQL Driver JAR not found in project libraries!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DATABASE CONNECTION ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return databaseItems;
    }
}
