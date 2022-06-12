package bg.softuni.PureWaterMiniCRM.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home");

        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView getAbout() {
        ModelAndView modelAndView = new ModelAndView("about");

        return modelAndView;
    }
}
