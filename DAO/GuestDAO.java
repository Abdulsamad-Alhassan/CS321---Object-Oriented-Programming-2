package G3PROJECT.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import G3PROJECT.mainClasses.Guest;

public class GuestDAO {

    public void addGuest(Guest guest) {
        int phone = guest.getPhone();
        String email = guest.getEmail();
        String username = guest.getUsername();
        String password = guest.getPassword();

        String INSERT = "INSERT INTO guests (phonenumber, email, username, password) VALUES (?, ?, ?, ?)";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, phone);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, password);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    guest.setGuestID(generatedId);
                    System.out.println("Guest inserted with ID: " + generatedId);
                } else {
                    System.out.println("Failed to retrieve generated guest ID.");
                }
            } else {
                System.out.println("Guest insert failed: No rows affected.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting guest: " + e.getMessage());
        }

        System.out.println("Final guest object ID: " + guest.getGuestID());
    }

    public Guest getGuestByID(int id) {
        String sql = "SELECT * FROM guests WHERE guest_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int phone = resultSet.getInt("phonenumber");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                return new Guest(id, phone, email, username, password);
            }

        } catch (SQLException e) {
            System.out.println("Error in getGuestByID: " + e.getMessage());
        }

        return null;
    }

    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();

        String sql = "SELECT * FROM guests";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                int guestID = resultSet.getInt("guest_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int phoneNumber = resultSet.getInt("phonenumber");

                Guest guest = new Guest(guestID, phoneNumber, email, username, password);
                guests.add(guest);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching guests: " + e.getMessage());
        }

        return guests;
    }

    public Guest getGuestByUserAndPass(String name, String password) {
        String sql = "SELECT * FROM guests WHERE username = ? AND password = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, name);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int guestID = resultSet.getInt("guest_id");
                int phonenumber = resultSet.getInt("phonenumber");
                String email = resultSet.getString("email");

                return new Guest(guestID, phonenumber, email, name, password);
            }

        } catch (SQLException e) {
            System.out.println("Error verifying guest login: " + e.getMessage());
        }

        return null;
    }
}
