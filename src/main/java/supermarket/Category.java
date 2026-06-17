/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */
public class Category {
    private int categoryID;
    private String categoryName;

    public Category(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() { return categoryID; }
    public String getCategoryName() { return categoryName; }

    // This ensures your UI dropdown shows the name, not a memory address
    @Override
    public String toString() {
        return categoryName;
    }
}
