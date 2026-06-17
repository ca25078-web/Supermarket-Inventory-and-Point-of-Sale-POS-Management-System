/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package supermarket;

import java.util.List;
/**
 *
 * @author Edwin
 */
public class MainDashboardUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainDashboardUI.class.getName());
    private DatabaseManager controller;

    // Change the default constructor to accept the controller
    public MainDashboardUI(DatabaseManager ctrl) {
        this.controller = ctrl;
        initComponents(); // Do not delete this! NetBeans needs it.
        refreshTable();   // Load data immediately
        
        // --- 1. OVERALL BACKGROUND ---
        getContentPane().setBackground(new java.awt.Color(245, 247, 250)); // Clean off-white
        
        // --- 2. STYLE THE JTABLE (HIGH CONTRAST & VISIBILITY) ---
        productTable.setRowHeight(28); // Taller rows are easier to read on projectors
        productTable.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Larger font
        
        // Darken the grid lines so they are clearly visible
        productTable.setShowGrid(true);
        productTable.setGridColor(new java.awt.Color(160, 160, 160)); // Darker gray grid
        
        // Optional: Set a very subtle off-white background so it isn't blindingly bright
        productTable.setBackground(new java.awt.Color(250, 250, 250));
        productTable.setForeground(new java.awt.Color(33, 37, 41)); // Near-black text for maximum contrast

        // Style the Table Header (Dark Blue background, White bold text)
        javax.swing.table.JTableHeader header = productTable.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        header.setOpaque(false);
        header.setBackground(new java.awt.Color(0, 76, 153)); // Darker, more serious blue
        header.setForeground(java.awt.Color.WHITE);
        
        // --- 3. APPLY BUTTON STYLES ---
        // Apply the standard blue style to your management buttons
        // (Make sure to change these names if your variable names are different!)
        applyStandardButtonStyle(btnAddProduct);
        applyStandardButtonStyle(btnManageCategories);
        applyStandardButtonStyle(btnManageSuppliers);
        applyStandardButtonStyle(btnRequestRestock);
        applyStandardButtonStyle(btnRefresh);
        
        // --- 4. STYLE DESTRUCTIVE & SECONDARY BUTTONS ---
        applyDangerButtonStyle(btnRemove); // Red for deleting!
        applySecondaryButtonStyle(btnBack); // Gray for navigation
    }
    
    // --- CORE LOGIC: Fetches fresh data and updates the JTable ---
    private void refreshTable() {
        // 1. Get the architecture of your table
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) productTable.getModel();
        
        // 2. Wipe the table completely clean
        model.setRowCount(0);
        
        // 3. Ask the DatabaseManager for the fresh list of objects
        java.util.List<Product> freshList = controller.getAllProducts();
        
        // 4. Loop through the list and rebuild the table rows
        for (Product p : freshList) {
            model.addRow(new Object[]{
                p.getProductID(),
                p.getProductName(),
                p.getPrice(),
                p.getStockQuantity(),
                p.getCategoryID()
            });
        }
    }
    
    // --- HELPER: Standard Blue Buttons ---
    private void applyStandardButtonStyle(javax.swing.JButton btn) {
        btn.setBackground(new java.awt.Color(0, 102, 204)); 
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(0, 76, 153)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(0, 102, 204)); 
            }
        });
    }

    // --- HELPER: Danger Red Buttons (Remove) ---
    private void applyDangerButtonStyle(javax.swing.JButton btn) {
        btn.setBackground(new java.awt.Color(220, 53, 69)); 
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(200, 35, 51)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(220, 53, 69)); 
            }
        });
    }

    // --- HELPER: Secondary Gray Buttons (Back) ---
    private void applySecondaryButtonStyle(javax.swing.JButton btn) {
        btn.setBackground(new java.awt.Color(108, 117, 125)); 
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(90, 98, 104)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new java.awt.Color(108, 117, 125)); 
            }
        });
    }
    
    // Add this helper method inside the class (UPGRADED TO PARSE COLLECTIONS)
    
    /**
     * Creates new form MainDashboardUI
     */
    public MainDashboardUI() {
        initComponents();
        
        btnBack.setBackground(new java.awt.Color(108, 117, 125)); // Subtle Secondary Gray
        btnBack.setForeground(java.awt.Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        btnManageCategories = new javax.swing.JButton();
        btnManageSuppliers = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        btnRemove = new javax.swing.JButton();
        btnRequestRestock = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Product Management Dashboard");

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Price (RM)", "Stock", "Category ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(3).setResizable(false);
            productTable.getColumnModel().getColumn(4).setResizable(false);
        }

        btnManageCategories.setBackground(new java.awt.Color(204, 204, 255));
        btnManageCategories.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnManageCategories.setText("Manage Categories");
        btnManageCategories.addActionListener(this::btnManageCategoriesActionPerformed);

        btnManageSuppliers.setBackground(new java.awt.Color(204, 204, 255));
        btnManageSuppliers.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnManageSuppliers.setText("Manage Suppliers");
        btnManageSuppliers.addActionListener(this::btnManageSuppliersActionPerformed);

        btnAddProduct.setBackground(new java.awt.Color(204, 204, 255));
        btnAddProduct.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnAddProduct.setText("Add Product");
        btnAddProduct.addActionListener(this::btnAddProductActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        jLabel1.setFont(new java.awt.Font("Sans Serif Collection", 1, 24)); // NOI18N
        jLabel1.setText("Inventory Management Dashboard");

        btnRemove.setBackground(new java.awt.Color(204, 204, 255));
        btnRemove.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnRemove.setText("Remove Selected");
        btnRemove.addActionListener(this::btnRemoveActionPerformed);

        jLayeredPane1.setLayer(btnRemove, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        btnRequestRestock.setBackground(new java.awt.Color(204, 204, 255));
        btnRequestRestock.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnRequestRestock.setText("Request Restock");
        btnRequestRestock.addActionListener(this::btnRequestRestockActionPerformed);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnManageCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnManageSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnRequestRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(186, 186, 186)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack)
                    .addComponent(btnRefresh))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnManageSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnManageCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRequestRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(224, 224, 224))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageSuppliersActionPerformed
    new SupplierFormUI(this, controller).setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnManageSuppliersActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
 // 1. Check if the user clicked a row in the table
        int selectedRow = productTable.getSelectedRow();
        
        if (selectedRow >= 0) {
            // UPDATE MODE: Extract the data from the clicked row
            int id = (int) productTable.getValueAt(selectedRow, 0);
            String name = (String) productTable.getValueAt(selectedRow, 1);
            double price = (double) productTable.getValueAt(selectedRow, 2);
            int stock = (int) productTable.getValueAt(selectedRow, 3);
            int catId = (int) productTable.getValueAt(selectedRow, 4);
            
            // Build the object and pass it to Constructor 2
            Product selectedProduct = ProductFactory.buildExistingProduct(id, name, price, stock, catId);
            new ProductFormUI(this, controller, selectedProduct).setVisible(true);
            
        } else {
            // ADD MODE: Open the blank form using Constructor 1
            new ProductFormUI(this, controller).setVisible(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnManageCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCategoriesActionPerformed
    new CategoryFormUI(this, controller).setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnManageCategoriesActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
    int selectedRow = productTable.getSelectedRow();
if (selectedRow >= 0) {
    // Get the ID from column 0
    int productId = (int) productTable.getValueAt(selectedRow, 0); 
    boolean success = controller.removeProduct(productId);
    if (success) {
        javax.swing.JOptionPane.showMessageDialog(this, "Product Removed!");
        refreshTable();
    }
} else {
    javax.swing.JOptionPane.showMessageDialog(this, "Please select a row first.");
}        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnRequestRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestRestockActionPerformed
int selectedRow = productTable.getSelectedRow();

// 1. Check if the user actually clicked a row in the table
if (selectedRow >= 0) {
    // 2. Get the product ID from the first column (index 0)
    int productId = (int) productTable.getValueAt(selectedRow, 0); 
    
    // 3. Pop up a box asking for the quantity
    String input = javax.swing.JOptionPane.showInputDialog(this, "Enter restock quantity to add:");
    
    if (input != null && !input.trim().isEmpty()) {
        try {
            int qty = Integer.parseInt(input.trim());
            
            // 4. Constraint Check: Quantity must be positive
            if (qty > 0) {
                boolean success = controller.requestRestock(productId, qty);
                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Restock Successful!");
                    refreshTable(); // Automatically refreshes the UI
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Database Error. Restock failed.");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
            }
        } catch (NumberFormatException ex) {
            // Exception Flow: User typed letters instead of numbers
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid whole number.");
        }
    }
} else {
    // Exception Flow: User clicked the button without highlighting a product
    javax.swing.JOptionPane.showMessageDialog(this, "Please select a product row from the table first.");
}        // TODO add your handling code here:
    }//GEN-LAST:event_btnRequestRestockActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // 1. Open the Main Menu again and hand the database connection back to it
        new SystemMainMenuUI(controller).setVisible(true);
        
        // 2. Destroy this current dashboard window
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
    refreshTable();        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // The application now starts here!
                new LoginUI().setVisible(true); 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnManageCategories;
    private javax.swing.JButton btnManageSuppliers;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestRestock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productTable;
    // End of variables declaration//GEN-END:variables
}
