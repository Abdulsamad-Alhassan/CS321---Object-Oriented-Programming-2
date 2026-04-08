package G3PROJECT.mainClasses;

public class Payment {
    private int paymentID;
    private double amount;
    private String paymentType;
    private String status;
    private int bookingID;

    public Payment(int paymentID, double amount, String paymentType, String status, int bookingID) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentType = paymentType;
        this.status = status;
        this.bookingID = bookingID;
    }

    public int getPaymentID() { return paymentID; }
    public void setPaymentID(int id) { this.paymentID = id; }
    public double getAmount() { return amount; }
    public String getPaymentType() { return paymentType; }
    public String getStatus() { return status; }
    public int getBookingID() { return bookingID; }
}
