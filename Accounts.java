package Example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Accounts {
    JFrame frame = new JFrame();

    JTextField tf_name = new JTextField();
    JTextField tf_mobile_no = new JTextField();
    JTextField tf_birthday = new JTextField();
    JTextField tf_email_add = new JTextField();
    JTextField tf_email_confirm = new JTextField();
    JPasswordField tf_pass = new JPasswordField();
    JPasswordField tf_pass_confirm = new JPasswordField();

    JComboBox<String> cmb_course = new JComboBox<String>(new String[]{"BSCS", "BSIT", "BSIS"});

    ButtonGroup bg_sem = new ButtonGroup();
    JRadioButton rb_1 = new JRadioButton("1st Semester");
    JRadioButton rb_2 = new JRadioButton("2nd Semester");

    DefaultListModel<String> listModel = new DefaultListModel<String>();
    JList<String> list_reciept = new JList<String>(listModel);

    JButton btn_add = new JButton("Add");
    JButton btn_reset = new JButton("Reset");
    JButton btn_clear = new JButton("Clear");

    Accounts() {
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var pnl_root = createVBox();
        pnl_root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        var pnl_title = new JPanel();
        pnl_title.add(new JLabel("ACCOUNT REGISTER"));

        var pnl_name_num = createVBox();
        var pnl_name = createHBox();
        pnl_name.add(new JLabel("Name: "), BorderLayout.WEST);
        pnl_name.add(tf_name, BorderLayout.CENTER);

        var pnl_no = createHBox();
        pnl_no.add(new JLabel("Mobile Number: "), BorderLayout.WEST);
        pnl_no.add(tf_mobile_no,BorderLayout.CENTER);

        pnl_name_num.add(pnl_name);
        pnl_name_num.add(pnl_no);

        var pnl_bday = createHBox();
        pnl_bday.add(new JLabel("Birthday: "), BorderLayout.WEST);
        pnl_bday.add(tf_birthday,BorderLayout.CENTER);

        var pnl_email_conf = createVBox();
        var pnl_email = createHBox();
        pnl_email.add(new JLabel("Email Address: "), BorderLayout.WEST);
        pnl_email.add(tf_email_add,BorderLayout.CENTER);

        var pnl_conf = createHBox();
        pnl_conf.add(new JLabel("Confirm Email: "), BorderLayout.WEST);
        pnl_conf.add(tf_email_confirm,BorderLayout.CENTER);

        pnl_email_conf.add(pnl_email);
        pnl_email_conf.add(pnl_conf);

        // Password
        var pnl_v_pass_conf = createVBox();
        var pnl_pass = createHBox();
        pnl_pass.add(new JLabel("Password: "), BorderLayout.WEST);
        pnl_pass.add(tf_pass,BorderLayout.CENTER);

        var pnl_pass_confirm = createHBox();
        pnl_pass_confirm.add(new JLabel("Password: "), BorderLayout.WEST);
        pnl_pass_confirm.add(tf_pass_confirm,BorderLayout.CENTER);

        pnl_v_pass_conf.add(pnl_pass);
        pnl_v_pass_conf.add(pnl_pass_confirm);

        // Course
        var pnl_course = createHBox();
        pnl_course.add(new JLabel("Course: "), BorderLayout.WEST);
        pnl_course.add(cmb_course,BorderLayout.CENTER);

        // Sem
        var pnl_sem = createHBox();
        pnl_sem.add(new JLabel("Semester: "), BorderLayout.WEST);
        var pnl_radio = createHBox();
        pnl_radio.add(rb_1, BorderLayout.WEST);
        pnl_radio.add(rb_2, BorderLayout.CENTER);

        bg_sem.add(rb_1);
        bg_sem.add(rb_2);
        pnl_sem.add(pnl_radio,BorderLayout.CENTER);

        // Buttons
        var pnl_buttons = createHBOX();
        pnl_buttons.add(btn_add);
        pnl_buttons.add(btn_reset);
        pnl_buttons.add(btn_clear);

        // Text Area

        // Root
        pnl_root.add(pnl_title);
        pnl_root.add(pnl_name_num);
        pnl_root.add(pnl_bday);
        pnl_root.add(pnl_email_conf);
        pnl_root.add(pnl_v_pass_conf);
        pnl_root.add(pnl_course);
        pnl_root.add(pnl_sem);
        pnl_root.add(new JScrollPane(list_reciept));
        pnl_root.add(pnl_buttons);

        // Adjust Size
        JPanel[] tfs = {pnl_name, pnl_no};
        for (JComponent c: tfs) {
//            c.setMaximumSize(new Dimension(Short.MAX_VALUE, 90));
        }

        btn_add.addActionListener((e) -> {
            String name, mobile, bday, course, email, email_conf;
            char[] password, password_conf;

            name = tf_name.getText();
            bday = tf_birthday.getText();
            mobile = tf_mobile_no.getText();
            email = tf_email_add.getText();
            email_conf = tf_email_confirm.getText();
            password = tf_pass.getPassword();
            password_conf = tf_pass_confirm.getPassword();
            course = String.valueOf(cmb_course.getSelectedItem());
            boolean isSelected = bg_sem.getSelection() != null;

            if (!name.isEmpty() && !mobile.isEmpty() && !bday.isEmpty() && !course.isEmpty() && !isSelected) {
                JOptionPane.showMessageDialog(frame, "A required field is missing, please enter all");
                return;
            }

            if (mobile.length() != 11) {
                JOptionPane.showMessageDialog(frame, "Mobile number format is incorrect");
                return;
            }

            if (!Arrays.equals(password, password_conf)) {
                JOptionPane.showMessageDialog(frame, "Password doesn't match");
                return;
            }

            if (!email.equals(email_conf)) {
                JOptionPane.showMessageDialog(frame, "Email doesn't match");
                return;
            }

            if (!email.endsWith("@yahoo.com") && !email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(frame, "Email doesn't belong to yahoo.com or gmail.com");
                return;
            }

            listModel.addElement(name + " - " + course);

        });

        btn_reset.addActionListener((e) -> {
            tf_name.setText("");
            tf_birthday.setText("");
            tf_mobile_no.setText("");
            tf_email_add.setText("");
            tf_email_confirm.setText("");
            tf_pass.setText("");
            tf_pass_confirm.setText("");
            cmb_course.setSelectedIndex(0);
            bg_sem.clearSelection();
        });

        btn_clear.addActionListener((e) -> {
            int[] selected = list_reciept.getSelectedIndices();
            Arrays.sort(selected);
            for (int i: selected) {
                listModel.removeElementAt(i);
            }
        });

        frame.add(pnl_root);
        frame.setVisible(true);
    }

    public JPanel createHBox() {
        var pnl = new JPanel(new BorderLayout(5, 3));
        pnl.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        return pnl;
    }

    public JPanel createVBox() {
        var pnl_root = new JPanel();
        pnl_root.setLayout(new BoxLayout(pnl_root, BoxLayout.Y_AXIS));
        return pnl_root;
    }

    public JPanel createHBOX() {
        var pnl_root = new JPanel();
        pnl_root.setLayout(new BoxLayout(pnl_root, BoxLayout.X_AXIS));
        return pnl_root;
    }

    public static void main(String[] arg) {
        SwingUtilities.invokeLater(() -> {
            new Accounts();
        });
    }
}
