/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.util.List;

/**
 * @author Edwin (Upgraded for Master Database Integration)
 */
public class StockController {
    
    // 1. Connect directly to YOUR working Master Database
    private DatabaseManager db = new DatabaseManager();

    // We can delete fetchCurrentStock() and InventoryData entirely, 
    // because DatabaseManager does the heavy lifting now!

    public javax.swing.table.DefaultTableModel getStockTableModel() {
        // 2. Define the exact table headers for the UI
        String[] columnNames = {"Product ID", "Product Name", "Current Stock", "Price (RM)", "Category ID"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0);
        
        // 3. Get the REAL data from your MySQL database using your method
        List<Product> stockList = db.getAllProducts();
        
        // 4. Fill the model with data using your Product getters
        for (Product item : stockList) {
            Object[] row = {
                item.getProductID(), 
                item.getProductName(), 
                item.getStockQuantity(), 
                item.getPrice(),
                item.getCategoryID()
            };
            model.addRow(row);
        }
        
        return model;
    }
}