package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.OrderAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.OrderService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService, UserService userService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("orderAddBindingModel")
    public OrderAddBindingModel addBindingModel() {
        return new OrderAddBindingModel();
    }

    @GetMapping("/add")
    public String getAddOrder(Model model) {

        model.addAttribute("customers",this.customerService.getAllCustomers());

        return "addOrder";
    }

    @PostMapping("/add")
    public String postAddOrder(@Valid OrderAddBindingModel orderAddBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddBindingModel",orderAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddBindingModel",bindingResult);

            return "redirect:/orders/add";
        }

        OrderServiceModel osm = this.modelMapper.map(orderAddBindingModel, OrderServiceModel.class);

        osm.setTotalPrice(osm.getProdCategory().getPrice().multiply(BigDecimal.valueOf(osm.getQuantity())));
        osm.setCustomer(this.customerService.findCustomerByCompanyName(orderAddBindingModel.getCustomer()));

        this.orderService.addOrder(osm, userDetails);

        return "redirect:/orders/all";
    }

    @GetMapping("/all")
    public String getAllSupps() {
        return "allOrders";
    }
}
