package G3PROJECT.mainClasses;

public class Guest extends Person {
    private int guestID;
    private String password;

    public Guest(int guestID, int phoneNumber, String email, String userName, String password) {
        super(guestID, email, userName, phoneNumber);
        this.guestID = guestID;
        this.password = password; 
    }

    public int getGuestID() {
        return this.guestID;
    }

    public void setGuestID(int id) {
        this.guestID = id;
    }

    public int getPhone(){
        return this.phoneNumber;
    }

    @Override
    public String toString() {
        return username + " (" + email + ")";
    }

    
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
