package ph.digipay.digipayelectroniclearning.models;

public class User {

    private String userID;
    private String username;
    private String password;

    public User() {
    }

    public User(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
