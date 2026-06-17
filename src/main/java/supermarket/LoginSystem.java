
package supermarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginSystem extends JFrame {
    private LoginInterface loginInterface;
    private LoginController loginController;
    private SupplierCredential database;

    public LoginSystem() {
        // Setup real database connection
        database = new SupplierCredential();

        loginController = new LoginController(database);
        loginInterface = new LoginInterface(loginController);

        setTitle("Supermarket POS - Supplier Login Portal");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Safe close
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new CardLayout());
        loginInterface.buildUI(mainPanel, this);
        
        add(mainPanel);
    }

    // --- Added back so you can test it! ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginSystem().setVisible(true);
        });
    }

    // ==========================================
    // BOUNDARY LAYER: Login Interface
    // ==========================================
    class LoginInterface {
        private LoginController controller;
        private JPanel cardPanel;
        private CardLayout cardLayout;
        
        private JTextField txtUserID;
        private JPasswordField txtPassword;

        public LoginInterface(LoginController controller) {
            this.controller = controller;
            this.cardLayout = new CardLayout();
            this.cardPanel = new JPanel(cardLayout);
        }

        public void buildUI(JPanel container, JFrame frame) {
            // Screen 1: Landing
            JPanel landingPanel = new JPanel(new GridBagLayout());
            JButton btnLoginIntent = new JButton("Go to Supplier Login");
            btnLoginIntent.setFont(new Font("Arial", Font.BOLD, 14));
            
            btnLoginIntent.addActionListener(e -> displayLogin());
            landingPanel.add(btnLoginIntent);

            // Screen 2: Login Form
            JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            txtUserID = new JTextField();
            txtPassword = new JPasswordField();
            JButton btnSubmit = new JButton("Login");

            formPanel.add(new JLabel("Supplier ID:"));
            formPanel.add(txtUserID);
            formPanel.add(new JLabel("Password:"));
            formPanel.add(txtPassword);

            JPanel formContainer = new JPanel(new BorderLayout(10, 10));
            formContainer.setBorder(BorderFactory.createTitledBorder("Enter Credentials"));
            formContainer.add(formPanel, BorderLayout.CENTER);
            formContainer.add(btnSubmit, BorderLayout.SOUTH);

            // Submit Button Action
            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String userID = txtUserID.getText().trim();
                    String password = new String(txtPassword.getPassword()).trim();
                    
                    boolean verified = controller.verifyCredentials(userID, password);
                    
                    if (verified) {
                        displaySuccessMessage();
                        
                        // === THE LOOP FIX ===
                        // 1. Destroy this login window
                        frame.dispose(); 
                        
                        // 2. Open your Master Dashboard!
                        // IMPORTANT: Change 'MainDashboardUI' to the actual name of your main menu file if it's different!
                        // NEW: Route to the Supplier's specific menu
                        new SupplierMainMenuUI(new DatabaseManager()).setVisible(true); 
                        
                    } else {
                        JOptionPane.showMessageDialog(frame, 
                            "Invalid Supplier ID or Password. Please try again.", 
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cardPanel.add(landingPanel, "LANDING");
            cardPanel.add(formContainer, "FORM");
            container.add(cardPanel);
        }

        public void displayLogin() {
            cardLayout.show(cardPanel, "FORM");
        }

        public void displaySuccessMessage() {
            JOptionPane.showMessageDialog(cardPanel, 
                "Login Successful!\nWelcome back to the Supermarket POS.", 
                "Authentication Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ==========================================
    // CONTROLLER LAYER: Login Controller
    // ==========================================
    class LoginController {
        private SupplierCredential credentialEntity;

        public LoginController(SupplierCredential credentialEntity) {
            this.credentialEntity = credentialEntity;
        }

        public boolean verifyCredentials(String userID, String password) {
            if (userID.isEmpty() || password.isEmpty()) {
                return false;
            }
            return credentialEntity.verifyRequest(userID, password);
        }
    }

    // ==========================================
    // ENTITY LAYER: Supplier Credential
    // ==========================================
    class SupplierCredential {
        // UPGRADE: Connected to XAMPP via DatabaseManager
        private DatabaseManager db = new DatabaseManager();

        public boolean verifyRequest(String userID, String password) {
            // Checks XAMPP to see if the user exists!
            return db.verifySupplierLogin(userID, password);
        }
    }
}
