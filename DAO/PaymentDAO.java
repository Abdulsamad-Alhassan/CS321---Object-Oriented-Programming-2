package G3PROJECT.DAO;

import G3PROJECT.mainClasses.Payment;

import java.sql.*;

public class PaymentDAO {

    public void addPayment(int bookingId, double amount, String paymentType, String status) {
        String INSERT = "INSERT INTO payments (booking_id, amount, payment_type, status) VALUES (?, ?, ?, ?)";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(INSERT);
        ) {
            statement.setInt(1, bookingId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentType);
            statement.setString(4, status);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Payment inserted for booking ID: " + bookingId + " with status: " + status);
            } else {
                System.out.println("Payment insert failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting payment: " + e.getMessage());
        }
    }

    public Payment getPaymentByBookingID(int bookingID) {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, bookingID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int paymentID = resultSet.getInt("payment_id");
                String paymentType = resultSet.getString("payment_type");
                int amount = resultSet.getInt("amount");
                String status = resultSet.getString("status");

                return new Payment(paymentID, amount, paymentType, status, bookingID);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching payment: " + e.getMessage());
        }

        return null;
    }

    public void updatePaymentStatus(int paymentID, String newStatus) {
        String sql = "UPDATE payments SET status = ? WHERE payment_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, newStatus);
            statement.setInt(2, paymentID);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Payment status updated.");
            } else {
                System.out.println("No payment found with ID: " + paymentID);
            }

        } catch (SQLException e) {
            System.out.println("Error updating payment: " + e.getMessage());
        }
    }
}
