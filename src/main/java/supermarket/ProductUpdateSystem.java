
package supermarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ProductUpdateSystem extends JFrame {
    private ProductManagerInterface managerInterface;
    private ProductManagerController managerController;
    private ProductEntity productEntity;

    public ProductUpdateSystem() {
        productEntity = new ProductEntity();
        managerController = new ProductManagerController(productEntity);
        managerInterface = new ProductManagerInterface(managerController);

        setTitle("Supermarket POS - Supplier Product Portal");
        setSize(800, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel containerPanel = new JPanel(new CardLayout());
        managerInterface.buildUI(containerPanel, this);

        add(containerPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductUpdateSystem().setVisible(true);
        });
    }

    // ==========================================
    // BOUNDARY LAYER: Product Manager Interface
    // ==========================================
    class ProductManagerInterface {
        private ProductManagerController controller;
        private JPanel cardPanel;
        private CardLayout cardLayout;

        private JList<String> productJList;
        private DefaultListModel<String> listModel;
        private JButton btnViewCatalog;

        // REMOVED: txtPrice! Suppliers only see Cost now.
        private JTextField txtProductID, txtName, txtStock, txtSupplierCost;
        private JComboBox<String> cmbStatus; 
        private JLabel lblFormTitle;
        private JButton btnSubmitForm;
        private boolean isEditMode = false; 

        public ProductManagerInterface(ProductManagerController controller) {
            this.controller = controller;
            this.cardLayout = new CardLayout();
            this.cardPanel = new JPanel(cardLayout);
        }

        public void buildUI(JPanel container, JFrame frame) {
            // --- SCREEN 1: CATALOG DASHBOARD ---
            JPanel catalogPanel = new JPanel(new BorderLayout(10, 10));
            catalogPanel.setBorder(BorderFactory.createTitledBorder("Supplier Catalog Control Dashboard"));

            listModel = new DefaultListModel<>();
            productJList = new JList<>(listModel);
            productJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            catalogPanel.add(new JScrollPane(productJList), BorderLayout.CENTER);

            JPanel topControl = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnViewCatalog = new JButton("Refresh / View Catalog");
            topControl.add(btnViewCatalog);
            catalogPanel.add(topControl, BorderLayout.NORTH);

            JPanel actionGrid = new JPanel(new GridLayout(1, 4, 10, 10));
            actionGrid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JButton btnAdd = new JButton("Add New Product");
            JButton btnEdit = new JButton("Edit / Update Product");
            JButton btnDelete = new JButton("Delete Product");
            
            actionGrid.add(btnAdd);
            actionGrid.add(btnEdit);
            actionGrid.add(btnDelete);
            catalogPanel.add(actionGrid, BorderLayout.SOUTH);

            btnViewCatalog.addActionListener(e -> refreshCatalogView());

            btnAdd.addActionListener(e -> {
                isEditMode = false;
                lblFormTitle.setText("Offer New Product to Supermarket");
                btnSubmitForm.setText("Submit to Catalog");
                
                txtProductID.setText(controller.generateNextID());
                txtName.setText("");
                txtStock.setText("");
                txtSupplierCost.setText("");
                cmbStatus.setSelectedIndex(0);
                
                cardLayout.show(cardPanel, "FORM_EDITOR");
            });

            btnEdit.addActionListener(e -> {
                String selectedValue = productJList.getSelectedValue();
                if (selectedValue == null) {
                    JOptionPane.showMessageDialog(frame, "Please select an item to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                isEditMode = true;
                lblFormTitle.setText("Modify Supplied Product Details");
                btnSubmitForm.setText("Update Details");

                String idStr = extractIDFromSelection(selectedValue);
                String[] details = controller.requestProductDetails(idStr);
                if (details != null) {
                    txtProductID.setText(details[0]);
                    txtName.setText(details[1]);
                    // Skip details[2] because that is the Admin's retail price!
                    txtStock.setText(details[3]);
                    txtSupplierCost.setText(details[4]);
                    cmbStatus.setSelectedItem(details[5]);
                    cardLayout.show(cardPanel, "FORM_EDITOR");
                }
            });

            btnDelete.addActionListener(e -> {
                String selectedValue = productJList.getSelectedValue();
                if (selectedValue == null) return;
                
                String idStr = extractIDFromSelection(selectedValue);
                int choice = JOptionPane.showConfirmDialog(frame, 
                        "Are you sure you want to withdraw Product ID: " + idStr + " from the catalog?", 
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (choice == JOptionPane.YES_OPTION) {
                    if (controller.deleteProduct(idStr)) {
                        JOptionPane.showMessageDialog(frame, "Product removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshCatalogView();
                    }
                }
            });

            // --- SCREEN 2: UNIFIED FORM VIEW ---
            JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Adjusted to 5 rows
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            txtProductID = new JTextField(); txtProductID.setEditable(false); 
            txtName = new JTextField();
            txtStock = new JTextField();
            txtSupplierCost = new JTextField(); 
            
            String[] statuses = {"Active", "Out of Stock", "Discontinued"};
            cmbStatus = new JComboBox<>(statuses); 

            formPanel.add(new JLabel("Product ID (Auto):")); formPanel.add(txtProductID);
            formPanel.add(new JLabel("Product Name:")); formPanel.add(txtName);
            formPanel.add(new JLabel("Your Wholesale Cost (RM):")); formPanel.add(txtSupplierCost);
            formPanel.add(new JLabel("Available Stock Quantity:")); formPanel.add(txtStock);
            formPanel.add(new JLabel("Product Status:")); formPanel.add(cmbStatus);

            JPanel formContainer = new JPanel(new BorderLayout(15, 15));
            lblFormTitle = new JLabel("Product Operations Form", JLabel.CENTER);
            lblFormTitle.setFont(new Font("Arial", Font.BOLD, 14));
            formContainer.add(lblFormTitle, BorderLayout.NORTH);
            formContainer.add(formPanel, BorderLayout.CENTER);

            JPanel formActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnCancel = new JButton("Cancel");
            btnSubmitForm = new JButton("Submit");
            formActions.add(btnCancel);
            formActions.add(btnSubmitForm);
            formContainer.add(formActions, BorderLayout.SOUTH);

            btnCancel.addActionListener(e -> cardLayout.show(cardPanel, "CATALOG_DASHBOARD"));

            btnSubmitForm.addActionListener(e -> {
                String id = txtProductID.getText();
                String name = txtName.getText().trim();
                String stock = txtStock.getText().trim();
                String cost = txtSupplierCost.getText().trim();
                String status = cmbStatus.getSelectedItem().toString();

                boolean success;
                if (isEditMode) {
                    success = controller.validateAndSaveUpdate(id, name, stock, cost, status);
                } else {
                    success = controller.validateAndSaveNewProduct(id, name, stock, cost, status);
                }

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Database Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshCatalogView();
                    cardLayout.show(cardPanel, "CATALOG_DASHBOARD");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error! Check numeric inputs.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            cardPanel.add(catalogPanel, "CATALOG_DASHBOARD");
            cardPanel.add(formContainer, "FORM_EDITOR");
            container.add(cardPanel);

            refreshCatalogView(); 
        }

        private void refreshCatalogView() {
            List<String> items = controller.requestCatalog();
            listModel.clear();
            for (String item : items) { listModel.addElement(item); }
        }

        private String extractIDFromSelection(String selectedValue) {
            return selectedValue.split(" - ")[0].replace("ID: ", "").trim();
        }
    }

    // ==========================================
    // CONTROLLER LAYER: Product Manager Controller
    // ==========================================
    class ProductManagerController {
        private ProductEntity productEntity;

        public ProductManagerController(ProductEntity productEntity) {
            this.productEntity = productEntity;
        }

        public List<String> requestCatalog() {
            List<String> list = new ArrayList<>();
            for (String[] r : productEntity.getRawDatabase()) {
                // UI now only shows the Supplier Cost
                list.add("ID: " + r[0] + " - " + r[1] + " | Supplier Cost: RM" + r[4] + " | Stock: " + r[3] + " [" + r[5] + "]");
            }
            return list;
        }

        public String[] requestProductDetails(String id) {
            return productEntity.getProductDetails(id);
        }

        public String generateNextID() {
            return productEntity.getCalculatedNextID();
        }

        public boolean validateAndSaveUpdate(String id, String name, String stock, String cost, String status) {
            if (!isValidInput(name, stock, cost)) return false;
            return productEntity.updateRequest(id, name, stock, cost, status);
        }

        public boolean validateAndSaveNewProduct(String id, String name, String stock, String cost, String status) {
            if (!isValidInput(name, stock, cost)) return false;
            return productEntity.addRequest(id, name, stock, cost, status);
        }

        public boolean deleteProduct(String id) {
            return productEntity.deleteRequest(id);
        }

        private boolean isValidInput(String name, String stock, String cost) {
            if (name.isEmpty() || stock.isEmpty() || cost.isEmpty()) return false;
            try {
                if (Double.parseDouble(cost) < 0) return false;
                if (Integer.parseInt(stock) < 0) return false;
                return true;
            } catch (NumberFormatException e) { return false; }
        }
    }

    // ==========================================
    // ENTITY LAYER: ProductEntity 
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

        public String getCalculatedNextID() {
            return String.valueOf(db.generateNextProductID());
        }

        public boolean updateRequest(String id, String name, String stock, String cost, String status) {
            // Securely calls the Supplier-specific update method
            return db.updateSupplierProduct(Integer.parseInt(id), name, Integer.parseInt(stock), Double.parseDouble(cost), status);
        }

        public boolean addRequest(String id, String name, String stock, String cost, String status) {
            // Securely calls the Supplier-specific add method
            return db.addSupplierProduct(Integer.parseInt(id), name, Integer.parseInt(stock), Double.parseDouble(cost), status);
        }

        public boolean deleteRequest(String id) {
            return db.deleteProduct(Integer.parseInt(id));
        }
    }
}