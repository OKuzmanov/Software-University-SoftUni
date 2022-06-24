package bg.softuni.examprepmusicdb.web;

import bg.softuni.examprepmusicdb.models.bindingModels.UsersLoginBindingModel;
import bg.softuni.examprepmusicdb.models.serviceModels.UserServiceModel;
import bg.softuni.examprepmusicdb.services.UserService;
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

    @ModelAttribute("usersLoginBindingModel")
    public UsersLoginBindingModel addUsersLoginBindingModel() {
        return new UsersLoginBindingModel();
    }

    @ModelAttribute("invalidCredentials")
    public boolean addInvalidCredentials() {
        return false;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid UsersLoginBindingModel usersLoginBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usersLoginBindingModel", usersLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usersLoginBindingModel", bindingResult);
            return "redirect:/users/login";
        }

        UserServiceModel usm = this.userService.findByUsername(usersLoginBindingModel.getUsername());

        if (usm == null || !usersLoginBindingModel.getPassword().equals(usm.getPassword())) {
            redirectAttributes.addFlashAttribute("usersLoginBindingModel", usersLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usersLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("invalidCredentials", true);
            return "redirect:/users/login";
        }

        this.userService.userLogin(usm);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {

        if (this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/users/login";
        }

        this.userService.logout();

        return "/";
    }
}
