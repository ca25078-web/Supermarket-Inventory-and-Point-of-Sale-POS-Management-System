package supermarket;

public class CartItem {
    private int productId; // Added so it links perfectly to MySQL
    private String productName;
    private double price;
    private int quantity;

    public CartItem(int id, String name, double price, int qty) {
        this.productId = id;
        this.productName = name;
        this.price = price;
        this.quantity = qty;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public double getSubTotal() { 
        return price * quantity; 
    }
    
    public String getInfo() { 
        return productName + " (" + quantity + ") - RM" + getSubTotal(); 
    }
}