package G3PROJECT.DAO;
import G3PROJECT.mainClasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDAO {

    private BookingDAO bookingDAO = new BookingDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private GuestDAO guestDAO = new GuestDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    public List<Room> viewAllRooms() {
        return roomDAO.getAllRooms(); 
    }

    public Room getRoomById(int roomID) {
        return roomDAO.getRoomById(roomID);
    }

    public void updateRoomStatus(Room room, String status) {
        roomDAO.updateRoomStatus(room, status);
    }

    public Admin getAdminByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int adminID = resultSet.getInt("admin_id");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phonenumber");
                return new Admin(adminID, phone, email, username, password);
            }

        } catch (SQLException e) {
            System.out.println("Error verifying admin: " + e.getMessage());
        }

        return null;
    }

    public List<Guest> viewAllGuests() {
        return guestDAO.getAllGuests();
    }

    public Guest getGuestById(int guestID) {
        return guestDAO.getGuestByID(guestID);
    }

    public List<Booking> viewAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public void cancelBooking(Booking booking) {
        bookingDAO.cancelBooking(booking);
    }

    public void checkInGuest(Booking booking, double amount, String paymentType, String status) {
        booking.setStatus("Checked-In");
        new BookingDAO().updateBookingStatus(booking);
        new PaymentDAO().addPayment(booking.getBookingID(), amount, paymentType, status);
        System.out.println("[DAO] Guest check-in & payment saved to DB.");
    }

    public void checkOutGuest(Booking booking) {
        bookingDAO.cancelBooking(booking);
        roomDAO.updateRoomStatus(booking.getRoom(), "Available");
        System.out.println("Guest checked out and booking deleted.");
    }

    public Payment getPaymentByBooking(Booking booking) {
        return paymentDAO.getPaymentByBookingID(booking.getBookingID());
    }

    public void updatePaymentStatus(Payment payment, String status) {
        paymentDAO.updatePaymentStatus(payment.getPaymentID(), status);
    }

    public void addPayment(int bookingId, double amount, String paymentType) {
        String INSERT = "INSERT INTO payments (booking_id, amount, payment_type) VALUES (?, ?, ?)";
        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = conn.prepareStatement(INSERT);
        ) {
            statement.setInt(1, bookingId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentType);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment inserted for booking ID: " + bookingId);
            } else {
                System.out.println("Payment insert failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting payment: " + e.getMessage());
        }
    }

    public void bookRoomForGuest(Booking booking) {
        bookingDAO.addBooking(booking);
        roomDAO.updateRoomStatus(booking.getRoom(), "Occupied");
    }
}
