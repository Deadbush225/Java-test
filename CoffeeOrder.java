import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeOrder extends JFrame {

    private final Map<String, Integer> drinkPrices = new LinkedHashMap<>() {{
        put("Espresso", 120);   put("Red Eye", 115);
        put("Black Eye", 115);  put("Americano", 117);
        put("Macchiato", 125);  put("Cappuccino", 126);
        put("Cafe Latte", 128);
    }};
    private final Map<JComponent, Integer> addonPrices = new LinkedHashMap<>();

    private final JComboBox<String> cmbx_drink;
    private final JLabel lbl_total = new JLabel("Total: 0");
    private final JPanel pnl_main = new JPanel(new GridBagLayout());
    private final GridBagConstraints gbc = new GridBagConstraints();

    public CoffeeOrder() {
        setTitle("Coffee Order");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 500);
        setLocationRelativeTo(null);

        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ROW 0: Drink ---
        // Label: stretchFactor 0 (don't grow). ComboBox: stretchFactor 1 (grow).
        addItem(0, 0, 1, 0, new JLabel("Drink:"));
        cmbx_drink = new JComboBox<>(drinkPrices.keySet().toArray(new String[0]));
        addItem(1, 0, 1, 1, cmbx_drink);

        // --- ROW 1-6: Styles ---
        addItem(0, 1, 2, 1, createHeader("BREWING STYLES")); // Header spans 2 cols, stretch 1

        ButtonGroup bg = new ButtonGroup();
        addToggle("Drip", 15, 2, bg);
        addToggle("Pour over", 17, 3, bg);
        addToggle("Cold", 12, 4, bg);
        addToggle("Espresso", 10, 5, bg);
        addToggle("Ristretto", 12, 6, bg);
        ((JRadioButton)addonPrices.keySet().iterator().next()).setSelected(true); // Select first

        // --- ROW 7-10: Add-ons ---
        addItem(0, 7, 2, 1, createHeader("ADD-ONS"));

        addToggle("Hot", 5, 8, null);
        addToggle("Bread", 15, 9, null);
        addToggle("Toasted Bread", 23, 10, null);

        // --- ROW 11-13: Calc ---
        addItem(0, 11, 2, 1, new JSeparator());

        JButton btn_calc = new JButton("Calculate Total");
        btn_calc.addActionListener(e -> calculate());

        addItem(0, 12, 2, 1, btn_calc);
        addItem(0, 13, 2, 1, lbl_total);

        add(pnl_main);
        setVisible(true);
    }

    private void calculate() {
        int total = drinkPrices.getOrDefault(cmbx_drink.getSelectedItem(), 0);
        for (Map.Entry<JComponent, Integer> entry : addonPrices.entrySet()) {
            if (((AbstractButton) entry.getKey()).isSelected()) total += entry.getValue();
        }
        lbl_total.setText("Total: " + total);
    }

    // Explicitly using stretchFactor for weightx
    private void addItem(int x, int y, int width, double stretchFactor, JComponent comp) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.weightx = stretchFactor;
        pnl_main.add(comp, gbc);
    }

    private void addToggle(String name, int price, int y, ButtonGroup bg) {
        AbstractButton btn = (bg == null) ? new JCheckBox(name + " (+" + price + ")") : new JRadioButton(name);
        if (bg != null) bg.add(btn);
        addonPrices.put(btn, price);
        addItem(1, y, 1, 1, btn); // Inputs always stretch (factor 1)
    }

    private JLabel createHeader(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.GRAY);
        return l;
    }

    public static void main(String[] args) {
        CoffeeOrder app = new CoffeeOrder();
    }
}