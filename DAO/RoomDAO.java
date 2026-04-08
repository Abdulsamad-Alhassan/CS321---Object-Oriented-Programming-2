package G3PROJECT.DAO;

import G3PROJECT.mainClasses.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String selectRoom = "SELECT * FROM rooms";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectRoom);
        ) {
            while (resultSet.next()) {
                int roomId = resultSet.getInt("room_id");
                int roomNumber = resultSet.getInt("room_number");
                String roomType = resultSet.getString("room_type");
                String roomStatus = resultSet.getString("status");

                Room room = new Room(roomId, roomNumber, roomType, roomStatus);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching rooms: " + e.getMessage());
        }

        for (Room r : rooms) {
            System.out.println("Room found: " + r.getRoomNumber() + " - " + r.getStatus());
        }

        return rooms;
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("room_number");
                String type = resultSet.getString("room_type");
                String status = resultSet.getString("status");

                return new Room(id, number, type, status);
            }

        } catch (SQLException e) {
            System.out.println("Error in getRoomById: " + e.getMessage());
        }

        return null;
    }

    public void updateRoomStatus(Room room, String newStatus) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vs", "root", "Yaznbash2002@");
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, room.getRoomID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating room status: " + e.getMessage());
        }
    }
}
