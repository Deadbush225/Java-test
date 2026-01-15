import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Airline extends JFrame {

    // --- DATA ---
    String[] domestic = {"Manila", "Cebu", "Davao"};
    String[] international = {"Manila", "Tokyo", "Singapore"};

    // --- COMPONENTS ---
    // Trip Type
    JRadioButton rOneWay = new JRadioButton("One Way", true);
    JRadioButton rRound = new JRadioButton("Round Trip");

    // Class Type
    JRadioButton rEco = new JRadioButton("Economy", true);
    JRadioButton rBus = new JRadioButton("Business");

    // Route
    JComboBox<String> cbFrom = new JComboBox<>(domestic);
    JComboBox<String> cbTo = new JComboBox<>(domestic);

    // Dates
    JTextField tfDate = new JTextField("2023-12-01", 10);

    // Add-ons (Checkboxes)
    JCheckBox chkBag = new JCheckBox("Baggage (+500)");
    JCheckBox chkMeal = new JCheckBox("Meal (+200)");
    JCheckBox chkIns = new JCheckBox("Insurance (+1000)");

    // Output Table
    DefaultTableModel tableModel;
    JTable table;

    // Layout Helpers
    JPanel pnlForm = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    public Airline() {
        setTitle("Finals: Airline Booking System");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. SETUP UI
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Row 0: Trip Type ---
        ButtonGroup bgTrip = new ButtonGroup(); bgTrip.add(rOneWay); bgTrip.add(rRound);
        addLabel("Trip Type:", 0, 0);
        addComp(panelOf(rOneWay, rRound), 1, 0);

        // --- Row 1: Flight Class ---
        ButtonGroup bgClass = new ButtonGroup(); bgClass.add(rEco); bgClass.add(rBus);
        addLabel("Class:", 0, 1);
        addComp(panelOf(rEco, rBus), 1, 1);

        // --- Row 2: Route ---
        addLabel("Route:", 0, 2);
        JPanel pRoute = new JPanel(new GridLayout(1, 2, 5, 0));
        pRoute.add(cbFrom); pRoute.add(cbTo);
        addComp(pRoute, 1, 2);

        // --- Row 3: Date ---
        addLabel("Date:", 0, 3);
        addComp(tfDate, 1, 3);

        // --- Row 4: Add-ons (Checkboxes) ---
        addLabel("Add-ons:", 0, 4);
        JPanel pAddons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pAddons.add(chkBag); pAddons.add(chkMeal); pAddons.add(chkIns);
        addComp(pAddons, 1, 4);

        // --- Row 5: Buttons ---
        JButton btnBook = new JButton("Book Flight");
        JButton btnClear = new JButton("Clear Selection");
        JPanel pBtns = new JPanel();
        pBtns.add(btnBook); pBtns.add(btnClear);
        gbc.gridwidth = 2; // Span across
        addComp(pBtns, 0, 5);

        // 2. SETUP TABLE (Bottom Section)
        String[] cols = {"Type", "Class", "Route", "Add-ons", "Total Price"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Booking History"));

        // 3. ADD PANELS TO FRAME
        add(pnlForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 4. EVENT LISTENERS

        // Logic: Calculate and Add to Table
        btnBook.addActionListener(e -> {
            String type = rOneWay.isSelected() ? "One Way" : "Round Trip";
            String cls = rEco.isSelected() ? "Economy" : "Business";
            String route = cbFrom.getSelectedItem() + " -> " + cbTo.getSelectedItem();

            // Basic Validation
            if (cbFrom.getSelectedItem().equals(cbTo.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Origin and Destination cannot be same!");
                return;
            }

            // Calculation
            int price = 3000; // Base Price
            if (rBus.isSelected()) price += 5000;
            if (rRound.isSelected()) price *= 2;

            StringBuilder addons = new StringBuilder();
            if (chkBag.isSelected()) { price += 500; addons.append("Bag "); }
            if (chkMeal.isSelected()) { price += 200; addons.append("Meal "); }
            if (chkIns.isSelected()) { price += 1000; addons.append("Ins "); }

            // Add Row to Table
            Object[] row = {type, cls, route, addons.toString(), "P " + price};
            tableModel.addRow(row);
        });

        // Logic: Clear Form
        btnClear.addActionListener(e -> {
            rOneWay.setSelected(true);
            rEco.setSelected(true);
            chkBag.setSelected(false); chkMeal.setSelected(false); chkIns.setSelected(false);
            cbFrom.setSelectedIndex(0); cbTo.setSelectedIndex(0);
        });

        setVisible(true);
    }

    // --- HELPER METHODS TO SHORTEN CODE ---

    private void addLabel(String text, int x, int y) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 0; gbc.gridwidth = 1;
        pnlForm.add(new JLabel(text), gbc);
    }

    private void addComp(JComponent comp, int x, int y) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 1;
        pnlForm.add(comp, gbc);
    }

    // Quick helper to group buttons in a panel for layout
    private JPanel panelOf(JComponent... comps) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        for (JComponent c : comps) p.add(c);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Airline::new);
    }
}