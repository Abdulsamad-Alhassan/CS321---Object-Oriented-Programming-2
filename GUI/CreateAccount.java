package G3PROJECT.GUI;

import G3PROJECT.DAO.GuestDAO;
import G3PROJECT.mainClasses.Guest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CreateAccount extends JFrame {

    private JTextField usernameField, phoneField, emailField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    public CreateAccount() {
        setTitle("Create Guest Account");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(204, 239, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Register");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(153, 221, 255));
        signUpButton.setFocusPainted(false);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(signUpButton, gbc);

        add(panel);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        
    }

    private void performRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        int phone;

        try {
            phone = Integer.parseInt(phoneField.getText().trim());

            Guest guest = new Guest(0, phone, email, username, password);
            new GuestDAO().addGuest(guest);
            JOptionPane.showMessageDialog(this, "Account created successfully.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phone number must be numeric.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
