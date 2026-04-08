package G3PROJECT.mainClasses;

import G3PROJECT.DAO.*;

public class Admin extends Person {
    private int adminID;

    public Admin(int adminID, int phoneNumber, String email, String userName, String password) {
        super(adminID, email, userName, phoneNumber); // ✅ Correct super call
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void checkInGuest(Booking booking, double amount, String paymentType, String status) {
        new AdminDAO().checkInGuest(booking, amount, paymentType, status);
    }
}
