package G3PROJECT.GUI;

import G3PROJECT.mainClasses.*;
import G3PROJECT.DAO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class BookRoomScreen extends JFrame {

    private Guest guest;
    private JComboBox<Room> roomDropdown;
    private JTextField checkInField, checkOutField;
    private JButton bookButton;

    public BookRoomScreen(Guest guest) {
        this.guest = guest;

        setTitle("Book a Room");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(204, 239, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Book Room");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(roomLabel);

        roomDropdown = new JComboBox<>();
        roomDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadAvailableRooms();
        panel.add(roomDropdown);

        panel.add(Box.createVerticalStrut(10));

        JLabel checkInLabel = new JLabel("Check-in Date (YYYY-MM-DD):");
        checkInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkInLabel);

        checkInField = new JTextField();
        checkInField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(checkInField);

        panel.add(Box.createVerticalStrut(10));

        JLabel checkOutLabel = new JLabel("Check-out Date (YYYY-MM-DD):");
        checkOutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkOutLabel);

        checkOutField = new JTextField();
        checkOutField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(checkOutField);

        panel.add(Box.createVerticalStrut(20));

        bookButton = new JButton("Book");
        bookButton.setBackground(new Color(153, 221, 255));
        bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookButton.setFocusPainted(false);
        panel.add(bookButton);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });
        

        add(panel);
    }

    private void loadAvailableRooms() {
        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms = roomDAO.getAllRooms();

        for (Room room : rooms) {
            if (room.getStatus().equalsIgnoreCase("Available")) {
                roomDropdown.addItem(room);
            }
        }
    }

    private void handleBooking() {
        try {
            Room selectedRoom = (Room) roomDropdown.getSelectedItem();
            LocalDate checkIn = LocalDate.parse(checkInField.getText().trim());
            LocalDate checkOut = LocalDate.parse(checkOutField.getText().trim());

            Booking booking = new Booking(guest, selectedRoom, checkIn, checkOut, "Booked", 0, null);
            BookingDAO bookingDAO = new BookingDAO();
            bookingDAO.addBooking(booking);

            JOptionPane.showMessageDialog(this, "Room booked successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
