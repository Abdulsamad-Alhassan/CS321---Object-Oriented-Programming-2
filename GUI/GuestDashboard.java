package G3PROJECT.GUI;

import G3PROJECT.DAO.BookingDAO;
import G3PROJECT.mainClasses.Booking;
import G3PROJECT.mainClasses.Guest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class GuestDashboard extends JFrame {

    private Guest guest;
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JButton bookRoomButton, cancelBookingButton, logoutButton, refreshButton;

    public GuestDashboard(Guest guest) {
        this.guest = guest;

        setTitle("Guest Dashboard - " + guest.getUsername());
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadBookings();

        setVisible(true);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        topPanel.setBackground(new Color(204, 239, 255));

        bookRoomButton = new JButton("Book Room");
        cancelBookingButton = new JButton("Cancel Booking");
        logoutButton = new JButton("Logout");
        refreshButton = new JButton("Refresh Table");

        topPanel.add(bookRoomButton);
        topPanel.add(cancelBookingButton);
        topPanel.add(refreshButton);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Booking ID", "Room Number", "Check-In", "Check-Out", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

    

        bookRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookRoomScreen(guest);
            }
        });

        cancelBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelSelectedBooking();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginScreen();
                dispose();
            }
        });
        
        
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRefresh(e);
            }
        });
    }

    private void handleRefresh(ActionEvent e) {
        loadBookings();
        JOptionPane.showMessageDialog(this, "Table refreshed.");
    }

    private void loadBookings() {
        tableModel.setRowCount(0);

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getAllBookings();

        for (Booking booking : bookings) {
            if (booking.getGuest().getGuestID() == guest.getGuestID()) {
                Object[] row = {
                    booking.getBookingID(),
                    booking.getRoom().getRoomNumber(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getStatus()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void cancelSelectedBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");
            return;
        }

        int bookingID = (int) tableModel.getValueAt(selectedRow, 0);
        BookingDAO bookingDAO = new BookingDAO();

        for (Booking booking : bookingDAO.getAllBookings()) {
            if (booking.getBookingID() == bookingID) {
                bookingDAO.cancelBooking(booking);
                break;
            }
        }

        JOptionPane.showMessageDialog(this, "Booking canceled.");
        loadBookings();
    }
}
