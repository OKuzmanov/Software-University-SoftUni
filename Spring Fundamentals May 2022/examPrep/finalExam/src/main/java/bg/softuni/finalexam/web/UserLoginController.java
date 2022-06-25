package bg.softuni.finalexam.web;

import bg.softuni.finalexam.models.bindingModels.UserLoginBindingModel;
import bg.softuni.finalexam.models.serviceModels.UserServiceModel;
import bg.softuni.finalexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;

    @Autowired
    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel addUserLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @ModelAttribute("areWrongCredentials")
    public boolean addWrongCred() {
        return false;
    }

    @GetMapping("/login")
    public String getLogin() {
        if (this.userService.getCurrentUser() != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (this.userService.getCurrentUser() != null) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:/users/login";
        }

        UserServiceModel usm = this.userService.findByUsername(userLoginBindingModel.getUsername());

        if (usm == null || !usm.getPassword().equals(userLoginBindingModel.getPassword())) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("areWrongCredentials", true);
            return "redirect:/users/login";
        }

        this.userService.loginUser(usm);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String getLogout() {

        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        this.userService.logoutUser();

        return "redirect:/";
    }
}
