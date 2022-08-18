package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.UserViewModel;
import bg.softuni.PureWaterMiniCRM.services.OrderService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public HomeController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        List<OrderViewModel> orderViewModels = this.orderService.fetchAll();
        model.addAttribute("allOrders", orderViewModels);

        double totalRevenue = orderViewModels.stream().map(OrderViewModel::getTotalPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        model.addAttribute("totalRevenue", String.format("%.2f",totalRevenue));

        List<UserViewModel> userViewModels = this.userService.fetchAll();
        model.addAttribute("allUsers", userViewModels);

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
