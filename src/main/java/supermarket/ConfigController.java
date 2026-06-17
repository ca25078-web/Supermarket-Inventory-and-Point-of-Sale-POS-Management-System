/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;



/**
 *
 * @author Edwin
 */
public class ConfigController {
    private ProductData productData = new ProductData();
    private InventoryModuleInterface externalSystem = new InventoryModuleInterface();

    public boolean updateThreshold(String itemID, int quantity) {
        if (validateInput(quantity)) {
            externalSystem.syncWithExternalSystem(); // Showcases integration
            return productData.setThreshold(itemID, quantity);
        }
        return false;
    }

    private boolean validateInput(int quantity) {
        return quantity >= 0; 
    }
}
