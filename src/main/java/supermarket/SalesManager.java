package supermarket;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalesManager {
    // Array for Chapter 6 requirements - KEPT FOR MARKS!
    private CartItem[] cart = new CartItem[100]; 
    private int count = 0;

    public boolean addProduct(CartItem item) {
        if (count < cart.length) {
            cart[count++] = item;
            return true;
        }
        return false; // Cart is full
    }

    // Array manipulation: Shifting elements when an item is removed
    public void removeProduct(int index) {
        if (index >= 0 && index < count) {
            for (int i = index; i < count - 1; i++) {
                cart[i] = cart[i + 1];
            }
            cart[--count] = null;
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < count; i++) {
            total += cart[i].getSubTotal();
        }
        return total;
    }

    public int getCount() { return count; }
    public CartItem[] getCart() { return cart; }
    
    public void clearCart() {
        cart = new CartItem[100];
        count = 0;
    }

    // Translates the Array into the format your DatabaseManager needs
    public List<Object[]> getCartDataForDB() {
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            CartItem item = cart[i];
            data.add(new Object[]{item.getProductId(), item.getProductName(), item.getPrice(), item.getQuantity(), item.getSubTotal()});
        }
        return data;
    }

    // Generates the physical receipt file
    public void saveTransaction() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "Invoice_" + timestamp + ".txt";
            FileWriter writer = new FileWriter(filename);
            
            writer.write("=====================================\n");
            writer.write("         SUPERMARKET POS             \n");
            writer.write("         OFFICIAL INVOICE            \n");
            writer.write("=====================================\n");
            writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");
            writer.write(String.format("%-20s %-5s %-10s\n", "Item", "Qty", "Subtotal"));
            writer.write("-------------------------------------\n");
            
            for (int i = 0; i < count; i++) {
                CartItem item = cart[i];
                writer.write(String.format("%-20s %-5d RM%.2f\n", item.getProductName(), item.getQuantity(), item.getSubTotal()));
            }
            
            writer.write("-------------------------------------\n");
            writer.write(String.format("%-26s RM%.2f\n", "GRAND TOTAL:", calculateTotal()));
            writer.write("=====================================\n");
            writer.write("       Thank you for shopping!       \n");
            writer.close();
            
        } catch (Exception e) {
            System.out.println("Could not print invoice: " + e.getMessage());
        }
    }
}