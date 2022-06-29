package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.util.validation.FieldMatch;
import bg.softuni.PureWaterMiniCRM.util.validation.UniqueEmail;
import bg.softuni.PureWaterMiniCRM.util.validation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords do not match.")
public class UserRegisterBindingModel {

    @NotBlank(message = "Username must not be blank!")
    @Size(min=2, max=20, message = "Username should be between 2 and 20 symbols long!")
    @UniqueUsername(message = "This username is already occupied!")
    private String username;

    @NotBlank(message = "First name should not be blank!")
    @Size(min=2, max=20, message = "First name should be between 2 and 20 characters long!")
    private String firstName;

    @NotBlank(message = "Last name should not be blank!")
    @Size(min=2, max=20, message = "Last name should be between 2 and 20 characters long!")
    private String lastName;

    @NotBlank(message = "Password should not be blank!")
    @Size(min=5, message = "Password should be at least 5 characters long!")
    private String password;

    @NotBlank(message = "Confirm password should not be blank!")
    @Size(min=5, message = "Confirm password should be at least 5 characters long!")
    private String confirmPassword;

    @NotBlank(message = "Email should not be blank!")
    @Email(message = "Email should be a valid email address!")
    @UniqueEmail(message = "This email is already occupied!")
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
