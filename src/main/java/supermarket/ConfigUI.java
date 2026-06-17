
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.util.Scanner;

public class ConfigUI {
    private ConfigController controller = new ConfigController();

    public void displayConfigMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- CONFIGURE THRESHOLDS ---");
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine().trim();
        System.out.print("Enter New Minimum Quantity: ");
        String qtyInput = scanner.nextLine().trim();
        
        try {
            int qty = Integer.parseInt(qtyInput);
            boolean success = controller.updateThreshold(itemID, qty);
            if (success) {
                System.out.println("Success: Threshold Updated Successfully!");
            } else {
                System.out.println("Error: Invalid input. Quantity must be a positive number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid whole number for quantity.");
        }
    }
}