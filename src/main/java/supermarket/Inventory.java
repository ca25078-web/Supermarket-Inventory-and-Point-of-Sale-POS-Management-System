package supermarket;

import java.util.HashMap;

public class Inventory {
    // Stores product name and its stock quantity
    private HashMap<String, Integer> stock = new HashMap<>();

    public Inventory() {
        // Pre-fill some sample data
        stock.put("apple", 50);
        stock.put("bottle", 20);
    }

    public boolean hasStock(String name, int qty) {
        return stock.containsKey(name) && stock.get(name) >= qty;
    }

    public void reduceStock(String name, int qty) {
        stock.put(name, stock.get(name) - qty);
    }
}