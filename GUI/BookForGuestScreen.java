package G3PROJECT.GUI;

import G3PROJECT.mainClasses.*;
import G3PROJECT.DAO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class BookForGuestScreen extends JFrame {

    private Admin admin;
    private JComboBox<Guest> guestDropdown;
    private JComboBox<Room> roomDropdown;
    private JTextField checkInField, checkOutField;
    private JButton bookButton;

    public BookForGuestScreen(Admin admin) {
        this.admin = admin;

        setTitle("Book Room for Guest");
        setSize(400, 400);
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

        JLabel title = new JLabel("Book Room for Guest");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        JLabel guestLabel = new JLabel("Select Guest:");
        guestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(guestLabel);

        guestDropdown = new JComboBox<>();
        guestDropdown.setMaximumSize(new Dimension(300, 25));
        guestDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGuests();
        panel.add(guestDropdown);

        panel.add(Box.createVerticalStrut(10));

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(roomLabel);

        roomDropdown = new JComboBox<>();
        roomDropdown.setMaximumSize(new Dimension(300, 25));
        roomDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadRooms();
        panel.add(roomDropdown);

        panel.add(Box.createVerticalStrut(10));

        JLabel checkInLabel = new JLabel("Check-in Date (YYYY-MM-DD):");
        checkInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkInLabel);

        checkInField = new JTextField();
        checkInField.setMaximumSize(new Dimension(300, 25));
        checkInField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkInField);

        panel.add(Box.createVerticalStrut(10));

        JLabel checkOutLabel = new JLabel("Check-out Date (YYYY-MM-DD):");
        checkOutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkOutLabel);

        checkOutField = new JTextField();
        checkOutField.setMaximumSize(new Dimension(300, 25));
        checkOutField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkOutField);

        panel.add(Box.createVerticalStrut(20));

        bookButton = new JButton("Book Room");
        bookButton.setBackground(new Color(153, 221, 255));
        bookButton.setFocusPainted(false);
        bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(bookButton);

        add(panel);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });
    }

    private void loadGuests() {
        List<Guest> guests = new AdminDAO().viewAllGuests();
        for (Guest g : guests) {
            guestDropdown.addItem(g);
        }
    }

    private void loadRooms() {
        List<Room> rooms = new AdminDAO().viewAllRooms();
        for (Room r : rooms) {
            if (r.getStatus().equalsIgnoreCase("Available")) {
                roomDropdown.addItem(r);
            }
        }
    }

    private void handleBooking() {
        try {
            Guest selectedGuest = (Guest) guestDropdown.getSelectedItem();
            Room selectedRoom = (Room) roomDropdown.getSelectedItem();
            LocalDate checkIn = LocalDate.parse(checkInField.getText().trim());
            LocalDate checkOut = LocalDate.parse(checkOutField.getText().trim());

            Booking booking = new Booking(selectedGuest, selectedRoom, checkIn, checkOut, "Booked", 0, null);
            new AdminDAO().bookRoomForGuest(booking);

            JOptionPane.showMessageDialog(this, "Room booked for " + selectedGuest.getUsername());
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
