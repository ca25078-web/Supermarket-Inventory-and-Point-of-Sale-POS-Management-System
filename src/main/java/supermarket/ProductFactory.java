/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */

public class ProductFactory {
    
   // Machine 1: Creates a brand NEW product (Upgraded with Polymorphism!)
    public static Product createNewProduct(String name, double price, int stock, int catId) {
        
        // If Category is 1 (e.g., Dairy/Fresh Produce), create the Child Object
        if (catId == 1) {
            return new PerishableProduct(name, price, stock, catId);
        } 
        // Otherwise, create the standard Parent Object
        else {
            return new Product(name, price, stock, catId);
        }
    }
    
    // Machine 2: Rebuilds an EXISTING product (Has an ID from the Database)
    public static Product buildExistingProduct(int id, String name, double price, int stock, int catId) {
        return new Product(id, name, price, stock, catId);
    }
}
