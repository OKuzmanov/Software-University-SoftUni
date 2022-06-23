package bg.softuni.examprepshoppinglist.web;

import bg.softuni.examprepshoppinglist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String getHome() {
        if (this.userService.getCurrentUser().getUsername() == null) {
            return "redirect:/users/login";
        }
        return "home";
    }
}
