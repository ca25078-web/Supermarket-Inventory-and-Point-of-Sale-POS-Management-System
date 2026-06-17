
package supermarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderSystem extends JFrame {
    private PurchaseOrderInterface orderInterface;
    private PurchaseOrderController orderController;
    private ProductEntity productEntity;

    public PurchaseOrderSystem() {
        productEntity = new ProductEntity();
        orderController = new PurchaseOrderController(productEntity);
        orderInterface = new PurchaseOrderInterface(orderController);
        orderController.setBoundaryInterface(orderInterface); 

        setTitle("Supermarket POS - Purchase Order Control");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Safe exit
        setLocationRelativeTo(null);

        JPanel containerPanel = new JPanel(new CardLayout());
        orderInterface.buildUI(containerPanel, this);

        add(containerPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PurchaseOrderSystem().setVisible(true);
        });
    }

    // ==========================================
    // BOUNDARY LAYER: Purchase Order Interface
    // ==========================================
    class PurchaseOrderInterface {
        private PurchaseOrderController controller;
        private JPanel cardPanel;
        private CardLayout cardLayout;

        private JList<String> itemJList;
        private DefaultListModel<String> listModel;

        private JLabel lblItemName, lblSupplierCost, lblAvailableStock;
        private JTextField txtQuantity, txtSupplierID; // NEW: Added Supplier ID tracker
        private String selectedItemID;

        public PurchaseOrderInterface(PurchaseOrderController controller) {
            this.controller = controller;
            this.cardLayout = new CardLayout();
            this.cardPanel = new JPanel(cardLayout);
        }

        public void buildUI(JPanel container, JFrame frame) {
            // --- SCREEN 1: BROWSE CATALOG ---
            JPanel browsePanel = new JPanel(new BorderLayout(10, 10));
            browsePanel.setBorder(BorderFactory.createTitledBorder("Available Marketplace Catalog"));

            listModel = new DefaultListModel<>();
            itemJList = new JList<>(listModel);
            browsePanel.add(new JScrollPane(itemJList), BorderLayout.CENTER);

            JButton btnSelect = new JButton("Initiate Purchase Order");
            browsePanel.add(btnSelect, BorderLayout.SOUTH);

            populateItemList();

            btnSelect.addActionListener(e -> {
                String selectedValue = itemJList.getSelectedValue();
                if (selectedValue == null) {
                    JOptionPane.showMessageDialog(frame, "Please highlight a catalog item first.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                selectedItemID = selectedValue.split(" - ")[0].replace("ID: ", "").trim();
                String[] details = controller.requestItemData(selectedItemID);
                
                if (details != null) {
                    // details array: [0]ID, [1]Name, [2]RetailPrice, [3]Stock, [4]SupplierCost, [5]Status
                    displayItemDetails(details[1], details[4], details[3]);
                }
            });

            // --- SCREEN 2: PURCHASE FORM ---
            JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Increased to 5 rows
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            lblItemName = new JLabel();
            lblSupplierCost = new JLabel();
            lblAvailableStock = new JLabel();
            txtSupplierID = new JTextField("SUP-"); // NEW
            txtQuantity = new JTextField("1");

            formPanel.add(new JLabel("Product Name:")); formPanel.add(lblItemName);
            formPanel.add(new JLabel("Wholesale Cost per Unit:")); formPanel.add(lblSupplierCost);
            formPanel.add(new JLabel("Current Supermarket Stock:")); formPanel.add(lblAvailableStock);
            formPanel.add(new JLabel("Target Supplier ID (e.g. SUP-101):")); formPanel.add(txtSupplierID);
            formPanel.add(new JLabel("Order Quantity:")); formPanel.add(txtQuantity);

            JPanel formContainer = new JPanel(new BorderLayout(10, 10));
            formContainer.setBorder(BorderFactory.createTitledBorder("Complete Purchase Request"));
            formContainer.add(formPanel, BorderLayout.CENTER);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnBack = new JButton("Cancel Order");
            JButton btnBuy = new JButton("Confirm & Restock");
            actions.add(btnBack);
            actions.add(btnBuy);
            formContainer.add(actions, BorderLayout.SOUTH);

            btnBack.addActionListener(e -> cardLayout.show(cardPanel, "BROWSE"));

            btnBuy.addActionListener(e -> {
                String qtyText = txtQuantity.getText().trim();
                String supplierId = txtSupplierID.getText().trim();
                
                if(supplierId.isEmpty() || supplierId.equals("SUP-")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid Supplier ID to log this order.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                controller.purchaseRequest(supplierId, selectedItemID, lblItemName.getText(), qtyText, lblSupplierCost.getText());
            });

            cardPanel.add(browsePanel, "BROWSE");
            cardPanel.add(formContainer, "DETAILS");
            container.add(cardPanel);
        }

        public void populateItemList() {
            listModel.clear();
            for (String[] raw : controller.getMarketCatalog()) {
                listModel.addElement("ID: " + raw[0] + " - " + raw[1] + " [Cost: RM" + raw[4] + "] [Stock: " + raw[3] + "]");
            }
        }

        public void displayItemDetails(String name, String cost, String stock) {
            lblItemName.setText(name);
            lblSupplierCost.setText(cost);
            lblAvailableStock.setText(stock);
            txtQuantity.setText("1");
            txtSupplierID.setText("SUP-"); // Reset for new order
            cardLayout.show(cardPanel, "DETAILS");
        }

        public void acceptOrder(double totalCost) {
            JOptionPane.showMessageDialog(cardPanel, 
                "Purchase Order Approved!\nTotal Paid to Supplier: RM" + String.format("%.2f", totalCost) + "\nYour supermarket inventory has been restocked.", 
                "Order Approved", JOptionPane.INFORMATION_MESSAGE);
            populateItemList(); 
            cardLayout.show(cardPanel, "BROWSE");
        }

        public void rejectOrder(String reason) {
            JOptionPane.showMessageDialog(cardPanel, "Order Failed: " + reason, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==========================================
    // CONTROLLER LAYER: Purchase Order Controller
    // ==========================================
    class PurchaseOrderController {
        private ProductEntity productEntity;
        private PurchaseOrderInterface boundaryInterface; 

        public PurchaseOrderController(ProductEntity productEntity) {
            this.productEntity = productEntity;
        }

        public void setBoundaryInterface(PurchaseOrderInterface boundaryInterface) {
            this.boundaryInterface = boundaryInterface;
        }

        public List<String[]> getMarketCatalog() {
            return productEntity.getRawDatabase();
        }

        public String[] requestItemData(String id) {
            return productEntity.getProductDetails(id);
        }

        public void purchaseRequest(String supplierId, String itemID, String itemName, String qtyText, String costText) {
            int quantityToBuy;
            double supplierCost;
            try {
                quantityToBuy = Integer.parseInt(qtyText);
                supplierCost = Double.parseDouble(costText);
                if (quantityToBuy <= 0) {
                    boundaryInterface.rejectOrder("Quantity must be greater than zero.");
                    return;
                }
            } catch (NumberFormatException nfe) {
                boundaryInterface.rejectOrder("Invalid quantity format.");
                return;
            }

            // Sends the request to the database
            boolean success = productEntity.processOrder(supplierId, itemID, itemName, quantityToBuy, supplierCost);
            
            if (success) {
                double total = quantityToBuy * supplierCost;
                boundaryInterface.acceptOrder(total);
            } else {
                boundaryInterface.rejectOrder("Database connection error during transaction.");
            }
        }
    }

    // ==========================================
    // ENTITY LAYER: ProductEntity (Connected to DB)
    // ==========================================
    class ProductEntity {
        private DatabaseManager db = new DatabaseManager();

        public List<String[]> getRawDatabase() {
            return db.getRawProductData();
        }

        public String[] getProductDetails(String id) {
            for (String[] row : getRawDatabase()) {
                if (row[0].equals(id)) return row;
            }
            return null;
        }

        // Uses the new Transaction method to restock inventory AND log the order!
        public boolean processOrder(String supplierId, String id, String name, int quantity, double cost) {
            return db.processPurchaseOrder(supplierId, Integer.parseInt(id), name, quantity, cost);
        }
    }
}
