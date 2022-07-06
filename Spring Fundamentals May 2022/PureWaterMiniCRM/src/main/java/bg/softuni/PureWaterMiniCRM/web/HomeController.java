package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/about")
    public String getAbout(@AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }
        return "about";
    }
}
