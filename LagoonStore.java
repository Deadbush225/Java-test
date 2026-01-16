import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LagoonStore {
    JFrame frame = new JFrame("PUP Lagoon Store");

    double subTotal = 0;

    JTextField tf_name = new JTextField(30);
    JTextField tf_course = new JTextField(30);

    HashMap<String, Double> pricingTable = new HashMap<String, Double>();

    ButtonGroup bg_items = new ButtonGroup();
    JCheckBox chk_shake = new JCheckBox("Shake");
    JCheckBox chk_fewa = new JCheckBox("Fewa");
    JCheckBox chk_shomaiRice = new JCheckBox("Shomai w/ Rice");
    JCheckBox chk_gulaman = new JCheckBox("Gulaman");
    JCheckBox chk_friedNoodles = new JCheckBox("Fried Noodles");
    JCheckBox chk_hotcake = new JCheckBox("Hotcake");
    JCheckBox chk_chickenRice = new JCheckBox("Chicken w/ Rice");
    JCheckBox chk_water = new JCheckBox("Water");
    JCheckBox[] chk_list = {
    chk_shake,
            chk_fewa,
    chk_shomaiRice,
            chk_gulaman,
    chk_friedNoodles,
            chk_hotcake,
    chk_chickenRice,
    chk_water};

    ButtonGroup bg_discount = new ButtonGroup();
    JRadioButton rb_pwd = new JRadioButton("PWD");
    JRadioButton rb_senior = new JRadioButton("SENIOR");
    JRadioButton rb_NO = new JRadioButton("NO DISCOUNT");

    JTextField tf_total = new JTextField(30);
    JTextField tf_moneyInserted = new JTextField(30);
    JTextField tf_change = new JTextField(30);
    JTextField tf_vat = new JTextField(30);

    JButton btn_confirm = new JButton("CONFIRM");
    JButton btn_cancel = new JButton("CANCEL");
    Font font = new Font("arial", Font.PLAIN, 20);

    LagoonStore() {
        frame.setSize(300, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        pricingTable.put("Shake", 18.25);
        pricingTable.put("Fewa", 26.99);
        pricingTable.put("Shomai w/ Rice", 46.42);
        pricingTable.put("Gulaman", 52.15);
        pricingTable.put("Fried Noodles", 60.60);
        pricingTable.put("Hotcake", 99.99);
        pricingTable.put("Chicken w/ Rice", 101.25);
        pricingTable.put("Water", 25.35);

        var pnl_root = createVBox();
        pnl_root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        var pnl_title = createHBox();
        pnl_title.add(new JLabel("PUP Lagoon Store"));

        var pnl_name = createHBox();
        pnl_name.add(new JLabel("Name: "));
        pnl_name.add(tf_name);

        var pnl_course = createHBox();
        pnl_course.add(new JLabel("Course: "));
        pnl_course.add(tf_course);

        // items
        var pnl_items = new JPanel(new GridLayout(4,2));
        pnl_items.add(chk_shake);
        pnl_items.add(chk_fewa);
        pnl_items.add(chk_shomaiRice);
        pnl_items.add(chk_gulaman);
        pnl_items.add(chk_friedNoodles);
        pnl_items.add(chk_hotcake);
        pnl_items.add(chk_chickenRice);
        pnl_items.add(chk_water);

        // Discount
        var pnl_discount = createHBox();
        pnl_discount.add(rb_pwd);
        pnl_discount.add(rb_senior);
        pnl_discount.add(rb_NO);
        bg_discount.add(rb_pwd);
        bg_discount.add(rb_senior);
        bg_discount.add(rb_NO);

        // Bottom
        var pnl_bottom = createVBox();

        var pnl_total = createHBox();
        pnl_total.add(new JLabel("TOTAL: "));
        pnl_total.add(tf_total);
        tf_total.setEnabled(false);
        tf_total.setDisabledTextColor(Color.DARK_GRAY);


        var pnl_money_inserted = createHBox();
        pnl_money_inserted.add(new JLabel("MONEY INSERTED: "));
        pnl_money_inserted.add(tf_moneyInserted);

        var pnl_change = createHBox();
        pnl_change.add(new JLabel("CHANGE: "));
        pnl_change.add(tf_change);
        tf_change.setEnabled(false);
        tf_change.setDisabledTextColor(Color.DARK_GRAY);

        var pnl_vat = createHBox();
        pnl_vat.add(new JLabel("VAT: "));
        pnl_vat.add(tf_vat);
        tf_vat.setEnabled(false);
        tf_vat.setDisabledTextColor(Color.DARK_GRAY);

        pnl_bottom.add(pnl_total);
        pnl_bottom.add(pnl_money_inserted);
        pnl_bottom.add(pnl_change);
        pnl_bottom.add(pnl_vat);

        // Confirm
        var pnl_confirm_cancel = createHBox();
        pnl_confirm_cancel.add(btn_confirm);
        pnl_confirm_cancel.add(btn_cancel);

        btn_confirm.addActionListener((e) -> {
            this.updateLabel();
        });

        pnl_root.add(pnl_title);
        pnl_root.add(pnl_name);
        pnl_root.add(pnl_course);
        pnl_root.add(new Label("ITEMS:"));
        pnl_root.add(pnl_items);
        pnl_root.add(new Label("DISCOUNT:"));
        pnl_root.add(pnl_discount);
        pnl_root.add(pnl_bottom);
        pnl_root.add(pnl_confirm_cancel);

        adjustSize();

        frame.add(pnl_root);
        frame.setVisible(true);
    }

    void updateLabel() {
        String moneyInserted = tf_moneyInserted.getText();
        double inserted = 0;
        try {
            inserted = Double.parseDouble(moneyInserted);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid payment input, please enter numerical character only", "Numbers only", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (moneyInserted.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please insert money first", "Missing Payment", JOptionPane.ERROR_MESSAGE);
            return;
        }

        subTotal = 0;

        for (JCheckBox ck: chk_list) {
            if (ck.isSelected()) {
                subTotal += pricingTable.get(ck.getText());
            }
        }

        double discount = 1;
        if (rb_pwd.isSelected()) {
            discount = 0.75;
        } else if (rb_senior.isSelected()) {
            discount = 0.7;
        }
        subTotal *= discount;

        double vat = subTotal * 0.12;
        double total = subTotal + vat;

        double change = inserted - total;

        if (inserted < total) {
            JOptionPane.showMessageDialog(frame, "Not enough balance", "Not Enough Payment", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tf_total.setText(String.format("%.2f", total));
        tf_change.setText(String.format("%.2f", change));
        tf_vat.setText(String.format("%.2f", vat));
    }

    public JPanel createVBox() {
        var h = new JPanel();
        h.setLayout(new BoxLayout(h, BoxLayout.Y_AXIS));
        return h;
    }

    public JPanel createHBox() {
        var h = new JPanel();
        h.setLayout(new BoxLayout(h, BoxLayout.X_AXIS));
        h.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));
        return h;
    }

    public void adjustSize() {
        // Set height to minimum
        JComponent[] c = {tf_course, tf_name, tf_change, tf_total, tf_vat, tf_moneyInserted};
        for (JComponent s: c) {
            s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        }
    }

    public static void main(String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LagoonStore();
            }
        });
    }
}
