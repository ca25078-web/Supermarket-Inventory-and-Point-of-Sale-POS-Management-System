/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */

public class Product implements Item { // <--- Added 'implements Item'
    private int productID;
    private String productName;
    private double price;
    private int stockQuantity;
    private int categoryID;

    // Constructor 1: For EXISTING products
    public Product(int productID, String productName, double price, int stockQuantity, int categoryID) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryID = categoryID;
    }

    // Constructor 2: For NEW products
    public Product(String productName, double price, int stockQuantity, int categoryID) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryID = categoryID;
    }

    // --- GETTERS ---
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public int getCategoryID() { return categoryID; }

    // --- INTERFACE IMPLEMENTATION [CO3: Polymorphism & Abstraction] ---
    @Override
    public double calculateTotalValue() {
        return this.price * this.stockQuantity;
    }

    @Override
    public String getItemType() {
        return "Regular Product";
    }
}
