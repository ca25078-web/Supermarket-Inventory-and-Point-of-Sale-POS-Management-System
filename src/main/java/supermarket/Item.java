/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package supermarket;

/**
 *
 * @author Edwin
 */

public interface Item {
    // Every item in the store must be able to calculate its total inventory value
    double calculateTotalValue();
    
    // Every item must be able to identify its type
    String getItemType();
}
