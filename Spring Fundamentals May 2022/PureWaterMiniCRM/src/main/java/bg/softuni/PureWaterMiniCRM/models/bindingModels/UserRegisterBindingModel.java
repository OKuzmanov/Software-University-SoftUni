package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotEmpty
    @Size(min=2, max=20)
    private String username;

    @NotEmpty
    @Size(min=2, max=20)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=20)
    private String lastName;

    @NotEmpty
    @Size(min=5)
    private String password;

    @NotEmpty
    @Email
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
