package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex() {
        if(this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome() {
        if(!this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/";
        }

        return "home";
    }

    @GetMapping("/about")
    public String getAbout() {
        if(this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/home";
        }
        return "about";
    }
}
