package bg.softuni.PureWaterMiniCRM.security;

public class CurrentUser {

    private String username;
    private String firstName;
    private String lastName;
    private boolean isLoggedIn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void clear() {
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.isLoggedIn = false;
    }
}
