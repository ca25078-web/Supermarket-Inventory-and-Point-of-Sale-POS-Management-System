/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

public class StockItem {
    private String id;
    private String name;
    private int quantity;
    private double price;
    private int threshold; // <-- Added to hold the low stock limit

    // Updated constructor to include the threshold
    public StockItem(String id, String name, int quantity, double price, int threshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.threshold = threshold;
    }

    // --- THESE EXACTLY MATCH YOUR SCREENSHOT ---
    
    public String getItemID() { 
        return id; 
    }
    
    public String getItemName() { 
        return name; 
    }
    
    public int getCurrentQuantity() { 
        return quantity; 
    }
    
    public int getLowStockThreshold() { 
        return threshold; 
    }
    
    public boolean isLowStock() {
        return this.quantity < this.threshold;
    }

    // --- KEPT THESE JUST IN CASE OTHER FILES USE THEM ---
    
    public String getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public double getPrice() { 
        return price; 
    }
}