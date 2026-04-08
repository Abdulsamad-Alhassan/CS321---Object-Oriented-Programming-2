package G3PROJECT.mainClasses;

 public class Person {
    protected int id;
    protected String email;
    protected String username;
    protected int phoneNumber;
    

    public Person(int id, String email, String username, int phoneNumber) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
    
    public int getPhoneNumber() { return phoneNumber; }
}
