/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */
// INHERITANCE: PerishableProduct is a child of Product
public class PerishableProduct extends Product {

    // Child Constructor just passes data up to the Parent using 'super'
    public PerishableProduct(String productName, double price, int stockQuantity, int categoryID) {
        super(productName, price, stockQuantity, categoryID);
    }

    // POLYMORPHISM & OVERRIDING: Changing how the math works for this specific child
    @Override
    public double calculateTotalValue() {
        // Perishable items lose 20% of their projected value due to spoilage risk!
        double standardValue = super.getPrice() * super.getStockQuantity();
        return standardValue * 0.80; 
    }

    @Override
    public String getItemType() {
        return "Perishable Product (Spoilage Risk Applied)";
    }
}
