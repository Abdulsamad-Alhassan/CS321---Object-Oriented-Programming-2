package G3PROJECT.DAO;

import G3PROJECT.mainClasses.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void addBooking(Booking booking) {
        int guest = booking.getGuest().getGuestID();
        int room = booking.getRoom().getRoomID(); 
        LocalDate checkOutDate = booking.getCheckOutDate();
        LocalDate checkInDate = booking.getCheckInDate();
        String status = booking.getStatus();

        String INSERT = "INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, guest);
            statement.setInt(2, room);
            statement.setDate(3, Date.valueOf(checkInDate));
            statement.setDate(4, Date.valueOf(checkOutDate));
            statement.setString(5, status);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    booking.setBookingID(id);
                    System.out.println("Booking inserted with ID: " + id);
                }

                new RoomDAO().updateRoomStatus(booking.getRoom(), "Booked");
                booking.setStatus("Booked");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String SelectData = "SELECT * FROM bookings";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SelectData);
        ) {
            while (resultSet.next()) {
                int booking_id = resultSet.getInt("booking_id");
                int guest_id = resultSet.getInt("guest_id");
                int room_id = resultSet.getInt("room_id");
                LocalDate checkOutDate = resultSet.getDate("check_out_date").toLocalDate();
                LocalDate checkInDate = resultSet.getDate("check_in_date").toLocalDate();
                String status = resultSet.getString("status");

                Guest guest = new GuestDAO().getGuestByID(guest_id);
                Room room = new RoomDAO().getRoomById(room_id);

                System.out.println("Booking ID: " + booking_id + 
                    " | Guest ID: " + guest_id + " → " + (guest == null ? "null" : "found") + 
                    " | Room ID: " + room_id + " → " + (room == null ? "null" : "found"));

                if (guest == null || room == null) {
                    System.out.println("Skipping booking ID " + booking_id + " due to missing guest or room.");
                    continue;
                }

                Booking booking = new Booking(guest, room, checkInDate, checkOutDate, status, booking_id, null);
                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }

        return bookings;
    }

    public void cancelBooking(Booking booking) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, booking.getBookingID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Booking canceled.");
                new RoomDAO().updateRoomStatus(booking.getRoom(), "Available");
            } else {
                System.out.println("No matching booking found to cancel.");
            }

        } catch (SQLException e) {
            System.out.println("Error canceling booking: " + e.getMessage());
        }
    }

    public void updateBookingStatus(Booking booking) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, booking.getStatus());
            statement.setInt(2, booking.getBookingID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Booking status updated to: " + booking.getStatus());
            } else {
                System.out.println("Booking not found for update.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating booking status: " + e.getMessage());
        }
    }
}
