/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import javax.swing.*;
import java.awt.*;

public class SupplierMainMenuUI extends JFrame {
    private DatabaseManager db;

    public SupplierMainMenuUI(DatabaseManager db) {
        this.db = db;
        setTitle("Supplier Portal - Main Menu");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kills app if they close here
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnUpdate = new JButton("Product Management");
        JButton btnOrder = new JButton("Purchase Orders");
        JButton btnHistory = new JButton("Supplier History");
        JButton btnExit = new JButton("Logout & Exit System");
        btnExit.setBackground(new Color(220, 20, 60));
        btnExit.setForeground(Color.WHITE);

        // Link buttons to your systems
        btnUpdate.addActionListener(e -> new ProductUpdateSystem().setVisible(true));
        btnOrder.addActionListener(e -> new PurchaseOrderSystem().setVisible(true));
        btnHistory.addActionListener(e -> new SupplierHistorySystem().setVisible(true));
        
        // Final Exit: Closes the whole app
        btnExit.addActionListener(e -> System.exit(0));

        panel.add(btnUpdate);
        panel.add(btnOrder);
        panel.add(btnHistory);
        panel.add(btnExit);

        add(panel);
    }
}
