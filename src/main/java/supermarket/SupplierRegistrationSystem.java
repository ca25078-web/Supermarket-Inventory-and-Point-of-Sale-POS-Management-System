package supermarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SupplierRegistrationSystem extends JFrame {
    // Boundary Object (Register Interface)
    private RegisterInterface registerInterface;
    // Controller Object
    private RegistrationController registrationController;
    // Entity Object (Data Store)
    private SupplierCredential database;

    public SupplierRegistrationSystem() {
        // Initialize layers to match the lifecycle in your friend's UML sequence diagram
        database = new SupplierCredential();
        registrationController = new RegistrationController(database);
        registerInterface = new RegisterInterface(registrationController);

        // Main window setup
        setTitle("Supermarket POS - Supplier Registration Portal");
        setSize(500, 400);
        
        // FIXED: Only close this window, not the whole application!
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initial view representing the Supplier actor reaching the system
        JPanel mainPanel = new JPanel(new CardLayout());
        registerInterface.buildUI(mainPanel, this);
        
        add(mainPanel);
    }

    // --- THIS IS THE MISSING PIECE TO TEST THE FILE ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SupplierRegistrationSystem().setVisible(true);
        });
    }
    
    // ==========================================
    // BOUNDARY LAYER: Register Interface
    // ==========================================
    class RegisterInterface {
        private RegistrationController controller;
        private JPanel cardPanel;
        private CardLayout cardLayout;
        
        private JTextField txtUserID;
        private JPasswordField txtPassword;

        public RegisterInterface(RegistrationController controller) {
            this.controller = controller;
            this.cardLayout = new CardLayout();
            this.cardPanel = new JPanel(cardLayout);
        }

        public void buildUI(JPanel container, JFrame frame) {
            // Screen 1: The Initial Entry Screen
            JPanel entryPanel = new JPanel(new GridBagLayout());
            JButton btnRegisterIntent = new JButton("Click to Register As Supplier");
            btnRegisterIntent.setFont(new Font("Arial", Font.BOLD, 14));
            
            btnRegisterIntent.addActionListener(e -> displayRegistration());
            entryPanel.add(btnRegisterIntent);

            // Screen 2: The Registration Form View
            JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            
            txtUserID = new JTextField();
            txtPassword = new JPasswordField();
            JButton btnSubmit = new JButton("Submit Registration");

            formPanel.add(new JLabel("Create Supplier ID:"));
            formPanel.add(txtUserID);
            formPanel.add(new JLabel("Create Password:"));
            formPanel.add(txtPassword);

            JPanel formContainer = new JPanel(new BorderLayout(10, 10));
            formContainer.setBorder(BorderFactory.createTitledBorder("Supplier Registration Details"));
            formContainer.add(formPanel, BorderLayout.CENTER);
            formContainer.add(btnSubmit, BorderLayout.SOUTH);

            // Event: Supplier clicks submit
            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String userID = txtUserID.getText().trim();
                    String password = new String(txtPassword.getPassword()).trim();
                    
                    // Sequence Arrow: insertDetails -> Triggers inputValidation on Controller
                    boolean success = controller.inputValidation(userID, password);
                    
                    if (success) {
                        displaySuccessMessage();
                        txtUserID.setText("");
                        txtPassword.setText("");
                        cardLayout.show(cardPanel, "ENTRY");
                    } else {
                        JOptionPane.showMessageDialog(frame, 
                            "Registration Failed! ID may already exist, or fields are blank.", 
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cardPanel.add(entryPanel, "ENTRY");
            cardPanel.add(formContainer, "FORM");
            container.add(cardPanel);
        }

        public void displayRegistration() {
            cardLayout.show(cardPanel, "FORM");
        }

        public void displaySuccessMessage() {
            JOptionPane.showMessageDialog(cardPanel, 
                "Supplier Profile Saved Successfully!\nYour registration data is now in the database.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ==========================================
    // CONTROLLER LAYER: Registration Controller
    // ==========================================
    class RegistrationController {
        private SupplierCredential credentialEntity;

        public RegistrationController(SupplierCredential credentialEntity) {
            this.credentialEntity = credentialEntity;
        }

        public boolean inputValidation(String userID, String password) {
            if (userID.isEmpty() || password.isEmpty()) {
                return false; 
            }
            return credentialEntity.saveRequest(userID, password);
        }
    }

    // ==========================================
    // ENTITY LAYER: Supplier Credential
    // ==========================================
    class SupplierCredential {
        // UPGRADE: We replaced the HashMap with your Master Database!
        private DatabaseManager db = new DatabaseManager();

        public boolean saveRequest(String userID, String password) {
            // This now permanently saves to XAMPP instead of deleting when the app closes
            return db.registerSupplier(userID, password);
        }
    }
}