package G3PROJECT.mainClasses;

public class Room {
    private int roomID;
    private int roomNumber;
    private String roomType;
    private String status;

    public Room(int roomID, int roomNumber, String roomType, String status) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status.equalsIgnoreCase("Available");
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - " + status;
    }
}
