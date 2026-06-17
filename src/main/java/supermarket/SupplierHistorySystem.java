
package supermarket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierHistorySystem extends JFrame {
    private ManageSupplierHistoryInterface historyInterface;
    private SupplierHistoryController historyController;
    private SupplierHistory historyEntity;

    public SupplierHistorySystem() {
        historyEntity = new SupplierHistory();
        historyController = new SupplierHistoryController(historyEntity);
        historyInterface = new ManageSupplierHistoryInterface(historyController);

        setTitle("Supermarket POS - Supplier History Log");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel containerPanel = new JPanel(new CardLayout());
        historyInterface.buildUI(containerPanel);
        add(containerPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupplierHistorySystem().setVisible(true));
    }

    // ==========================================
    // BOUNDARY LAYER: History Interface
    // ==========================================
    class ManageSupplierHistoryInterface {
        private SupplierHistoryController controller;
        private JPanel cardPanel;
        private CardLayout cardLayout;
        private JTextField txtSearch;
        private DefaultTableModel tableModel;

        public ManageSupplierHistoryInterface(SupplierHistoryController controller) {
            this.controller = controller;
            this.cardLayout = new CardLayout();
            this.cardPanel = new JPanel(cardLayout);
        }

        public void buildUI(JPanel container) {
            JPanel dashboardPanel = new JPanel(new BorderLayout(10, 10));
            dashboardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtSearch = new JTextField(15);
            JButton btnSearch = new JButton("Search by ID");
            JButton btnAll = new JButton("Show All");
            searchPanel.add(new JLabel("Supplier ID:"));
            searchPanel.add(txtSearch);
            searchPanel.add(btnSearch);
            searchPanel.add(btnAll);
            dashboardPanel.add(searchPanel, BorderLayout.NORTH);

            String[] cols = {"PO ID", "Supplier ID", "Product", "Date", "Status"};
            tableModel = new DefaultTableModel(cols, 0);
            dashboardPanel.add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

            btnSearch.addActionListener(e -> updateTable(controller.searchRequest(txtSearch.getText().trim())));
            btnAll.addActionListener(e -> updateTable(controller.searchRequest("")));

            cardPanel.add(dashboardPanel, "DASHBOARD");
            container.add(cardPanel);
            updateTable(controller.searchRequest("")); // Initial load
        }

        private void updateTable(List<String[]> logs) {
            tableModel.setRowCount(0);
            for (String[] row : logs) tableModel.addRow(row);
        }
    }

    // ==========================================
    // CONTROLLER & ENTITY LAYERS
    // ==========================================
    class SupplierHistoryController {
        private SupplierHistory historyEntity;
        public SupplierHistoryController(SupplierHistory h) { this.historyEntity = h; }
        public List<String[]> searchRequest(String id) { return historyEntity.searchRecord(id); }
    }

    class SupplierHistory {
        private DatabaseManager db = new DatabaseManager();
        public List<String[]> searchRecord(String id) { return db.getSupplierHistory(id); }
    }
}
