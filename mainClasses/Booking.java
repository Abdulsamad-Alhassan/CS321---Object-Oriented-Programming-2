package G3PROJECT.mainClasses;

import java.time.LocalDate;

public class Booking {

    Guest guest;
    Room room; 
    LocalDate checkOutDate;
    LocalDate checkInDate;
    String status;
    int bookingID;
    Payment payment;
    

    public Booking(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate, String status, int bookingID, Payment payment){
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.bookingID = bookingID;
        this.payment = payment;
    }

    public void setBookingID(int ID){
        this.bookingID = ID;
    }

    public int getBookingID(){
        return this.bookingID;
    }

    public Guest getGuest(){
        return this.guest;
    }
    
    public Room getRoom(){
        return this.room;
    }

    public LocalDate getCheckInDate(){
        return this.checkInDate;
    }

    public LocalDate getCheckOutDate(){
        return this.checkOutDate;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public Payment getPayment(){
        return this.payment;
    }

}
