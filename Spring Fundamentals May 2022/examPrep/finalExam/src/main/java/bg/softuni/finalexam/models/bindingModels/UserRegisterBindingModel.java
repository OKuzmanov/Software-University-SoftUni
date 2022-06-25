package bg.softuni.finalexam.models.bindingModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @NotBlank
    @Size(min = 3)
    private String password;

    @NotBlank
    @Size(min = 3)
    private String confirmPassword;

    @NotBlank
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
