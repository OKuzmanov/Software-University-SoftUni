package bg.softuni.examprep_springfund.web;

import bg.softuni.examprep_springfund.model.view.OrderViewModel;
import bg.softuni.examprep_springfund.security.CurrentUser;
import bg.softuni.examprep_springfund.service.OrderService;
import bg.softuni.examprep_springfund.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public HomeController(CurrentUser currentUser, OrderService orderService, UserService userService) {
        this.currentUser = currentUser;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/home")
    private String getIndex(Model model){

        if(currentUser.getId() == null) {
            return "index";
        }

        List<OrderViewModel> allOrders = orderService.findAllOrdersOrderedByPriceDesc();
        model.addAttribute("orders", allOrders);
        model.addAttribute("totalTime", allOrders.stream().map(o -> o.getCategory().getNeededTime()).mapToInt(e -> e).sum());
        model.addAttribute("employees", userService.findAllUsersOrderedByNumberOfOrdersDesc());

        return "home";
    }
}
