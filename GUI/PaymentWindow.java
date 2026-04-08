package G3PROJECT.GUI;

import G3PROJECT.mainClasses.*;
import G3PROJECT.DAO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaymentWindow extends JFrame {

    private Booking booking;
    private JTextField amountField;
    private JComboBox<String> paymentTypeDropdown;
    private JComboBox<String> statusDropdown;
    private JButton confirmButton;

    public PaymentWindow(Booking booking) {
        this.booking = booking;

        setTitle("Complete Payment");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(204, 239, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Complete Payment");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amountLabel);

        amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(200, 30));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amountField);
        panel.add(Box.createVerticalStrut(10));

        JLabel typeLabel = new JLabel("Payment Type:");
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(typeLabel);

        paymentTypeDropdown = new JComboBox<>(new String[] { "Cash", "Card", "Online" });
        paymentTypeDropdown.setMaximumSize(new Dimension(200, 30));
        paymentTypeDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(paymentTypeDropdown);
        panel.add(Box.createVerticalStrut(10));

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(statusLabel);

        statusDropdown = new JComboBox<>(new String[] { "Paid", "Pending" });
        statusDropdown.setMaximumSize(new Dimension(200, 30));
        statusDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(statusDropdown);
        panel.add(Box.createVerticalStrut(20));

        confirmButton = new JButton("Confirm Payment");
        confirmButton.setBackground(new Color(153, 221, 255));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(confirmButton);

        add(panel);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performCheckInWithPayment();
            }
        });
    }

    private void performCheckInWithPayment() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            String paymentType = (String) paymentTypeDropdown.getSelectedItem();
            String status = (String) statusDropdown.getSelectedItem();

            new AdminDAO().checkInGuest(booking, amount, paymentType, status);
            JOptionPane.showMessageDialog(this, "Guest checked in and payment saved.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
