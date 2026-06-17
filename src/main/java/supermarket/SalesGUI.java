package supermarket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesGUI extends JFrame {
    // UI Components
    private JTextField txtSearch, txtQty;
    private JButton btnAddToCart, btnRemoveItem, btnCheckout, btnClose;
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JLabel lblTotal;
    
    // System Integration
    private DatabaseManager db = new DatabaseManager();
    private SalesManager salesManager = new SalesManager(); 

    public SalesGUI() {
        setTitle("Supermarket POS - Advanced Checkout System");
        setSize(750, 500); // Made slightly wider to fit the new button
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- TOP PANEL ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(15);
        txtQty = new JTextField(5);
        btnAddToCart = new JButton("Add to Cart");
        
        // UPGRADE: Changed label to instruct the user
        topPanel.add(new JLabel("Product Name or ID:"));
        topPanel.add(txtSearch);
        topPanel.add(new JLabel("Qty:"));
        topPanel.add(txtQty);
        topPanel.add(btnAddToCart);
        add(topPanel, BorderLayout.NORTH);

        // --- CENTER PANEL ---
        String[] columns = {"ID", "Product Name", "Unit Price (RM)", "Qty", "Subtotal (RM)"};
        cartModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(cartModel);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // --- BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close"); // NEW CLOSE BUTTON
        btnRemoveItem = new JButton("Remove Selected Item");
        btnCheckout = new JButton("Checkout & Print Invoice");
        btnCheckout.setBackground(new Color(34, 139, 34)); 
        btnCheckout.setForeground(Color.WHITE);
        
        lblTotal = new JLabel("Total: RM 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));

        bottomPanel.add(btnClose);
        bottomPanel.add(btnRemoveItem);
        bottomPanel.add(btnCheckout);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(lblTotal);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- EVENT LISTENERS ---
        
        // 1. ADD TO CART (Upgraded for Name OR ID)
        btnAddToCart.addActionListener(e -> {
            String searchInput = txtSearch.getText().trim();
            try {
                int qty = Integer.parseInt(txtQty.getText().trim());
                if (qty <= 0) throw new NumberFormatException();

                List<Product> allProducts = db.getAllProducts();
                Product found = null;
                
                // UPGRADED SEARCH LOGIC
                for (Product p : allProducts) {
                    // Check if input matches Name (ignoring case) OR matches the ID exactly
                    if (p.getProductName().equalsIgnoreCase(searchInput) || String.valueOf(p.getProductID()).equals(searchInput)) {
                        found = p; 
                        break;
                    }
                }

                if (found != null) {
                    if (found.getStockQuantity() >= qty) {
                        CartItem newItem = new CartItem(found.getProductID(), found.getProductName(), found.getPrice(), qty);
                        salesManager.addProduct(newItem);
                        refreshCartTable();
                        txtSearch.setText(""); 
                        txtQty.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Only " + found.getStockQuantity() + " items left in stock!", "Low Stock", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Product not found! Check the Name or ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. REMOVE ITEM
        btnRemoveItem.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow >= 0) {
                salesManager.removeProduct(selectedRow); 
                refreshCartTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item from the cart to remove.");
            }
        });

    // 3. CHECKOUT
        btnCheckout.addActionListener(e -> {
            if (salesManager.getCount() == 0) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }

            // Sends the cart to the DatabaseManager to do the SQL work
            boolean success = db.processCartCheckout(salesManager.getCartDataForDB());

            if (success) {
                // REMOVED: salesManager.saveTransaction();
                
                JOptionPane.showMessageDialog(this, "Payment Successful! Records saved to database.\nTotal Paid: RM " + String.format("%.2f", salesManager.calculateTotal()));
                salesManager.clearCart();
                refreshCartTable();
            } else {
                JOptionPane.showMessageDialog(this, "Checkout Failed! An item in the cart may have run out of stock.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. CLOSE WINDOW (NEW)
        btnClose.addActionListener(e -> {
            this.dispose(); // Safely closes the Sales window and returns you to Main Menu
        });
    }

    // Helper Method: Syncs the UI table with your friend's Array
    private void refreshCartTable() {
        cartModel.setRowCount(0); 
        CartItem[] currentCart = salesManager.getCart();
        
        for (int i = 0; i < salesManager.getCount(); i++) {
            CartItem item = currentCart[i];
            cartModel.addRow(new Object[]{item.getProductId(), item.getProductName(), item.getPrice(), item.getQuantity(), item.getSubTotal()});
        }
        lblTotal.setText("Total: RM " + String.format("%.2f", salesManager.calculateTotal()));
    }
}