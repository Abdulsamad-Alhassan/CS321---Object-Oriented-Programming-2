package G3PROJECT.GUI;

import G3PROJECT.mainClasses.*;
import G3PROJECT.DAO.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private Admin admin;
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    private JButton checkInButton, checkOutButton, cancelBookingButton;
    private JButton viewGuestsButton, viewRoomsButton, bookForGuestButton, logoutButton, refreshButton;

    public AdminDashboard(Admin admin) {
        this.admin = admin;

        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadBookings();

        setVisible(true);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(new Color(204, 239, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkInButton = new JButton("Check In");
        checkOutButton = new JButton("Check Out");
        cancelBookingButton = new JButton("Cancel Booking");
        viewGuestsButton = new JButton("View Guests");
        viewRoomsButton = new JButton("View Rooms");
        bookForGuestButton = new JButton("Book for Guest");
        logoutButton = new JButton("Logout");
        refreshButton = new JButton("Refresh Table");

        JButton[] buttons = {
            checkInButton, checkOutButton, cancelBookingButton,
            viewGuestsButton, viewRoomsButton, bookForGuestButton,
            refreshButton, logoutButton
        };

        for (JButton button : buttons) {
            button.setBackground(new Color(153, 221, 255));
            button.setFocusPainted(false);
            topPanel.add(button);
            topPanel.add(Box.createHorizontalStrut(10));
        }

        add(topPanel, BorderLayout.NORTH);

        String[] columns = { "Booking ID", "Guest", "Room", "Check-In", "Check-Out", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);

        add(scrollPane, BorderLayout.CENTER);

        checkInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Booking b = getSelectedBooking();
                if (b != null) {
                    new PaymentWindow(b);
                }
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBookings();
                JOptionPane.showMessageDialog(null, "Table refreshed.");
            }
        });
        
        checkOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performCheckOut();
            }
        });
        
        cancelBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performCancelBooking();
            }
        });
        
        viewGuestsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGuests();
            }
        });
        
        viewRoomsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRooms();
            }
        });
        
        bookForGuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookForGuestScreen(admin);
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
    }

    private void loadBookings() {
        tableModel.setRowCount(0);
        AdminDAO dao = new AdminDAO();
        List<Booking> bookings = dao.viewAllBookings();

        for (Booking b : bookings) {
            tableModel.addRow(new Object[]{
                b.getBookingID(),
                b.getGuest().getUsername(),
                b.getRoom().getRoomNumber(),
                b.getCheckInDate(),
                b.getCheckOutDate(),
                b.getStatus()
            });
        }
    }

    private Booking getSelectedBooking() {
        int row = bookingTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking.");
            return null;
        }

        int bookingId = (int) tableModel.getValueAt(row, 0);
        return new AdminDAO().viewAllBookings().stream()
                .filter(b -> b.getBookingID() == bookingId)
                .findFirst()
                .orElse(null);
    }

    private void performCheckOut() {
        Booking b = getSelectedBooking();
        if (b != null) {
            if (!b.getStatus().equalsIgnoreCase("Checked-In")) {
                JOptionPane.showMessageDialog(this, "You can only check out guests who are already checked in.");
                return;
            }

            new AdminDAO().checkOutGuest(b);
            JOptionPane.showMessageDialog(this, "Guest checked out. Booking deleted.");
            loadBookings();
        }
    }

    private void performCancelBooking() {
        Booking b = getSelectedBooking();
        if (b != null) {
            new AdminDAO().cancelBooking(b);
            JOptionPane.showMessageDialog(this, "Booking canceled.");
            loadBookings();
        }
    }

    private void showGuests() {
        AdminDAO dao = new AdminDAO();
        List<Guest> guests = dao.viewAllGuests();

        StringBuilder sb = new StringBuilder("All Guests:\n\n");
        for (Guest g : guests) {
            sb.append("- ").append(g.getUsername()).append(" (").append(g.getEmail()).append(")\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void showRooms() {
        AdminDAO dao = new AdminDAO();
        List<Room> rooms = dao.viewAllRooms();

        StringBuilder sb = new StringBuilder("All Rooms:\n\n");
        for (Room r : rooms) {
            sb.append("- Room ").append(r.getRoomNumber()).append(" [")
              .append(r.getRoomType()).append("] - ").append(r.getStatus()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
