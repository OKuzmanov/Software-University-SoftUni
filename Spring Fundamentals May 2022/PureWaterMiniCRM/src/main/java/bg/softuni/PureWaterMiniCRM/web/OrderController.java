package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.OrderAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.OrderHistoryService;
import bg.softuni.PureWaterMiniCRM.services.OrderService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService, OrderHistoryService orderHistoryService, UserService userService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("orderAddBindingModel")
    public OrderAddBindingModel addBindingModel() {
        return new OrderAddBindingModel();
    }

    @ModelAttribute("isNotSuccessDel")
    public boolean addIsNotSuccessDel() {
        return false;
    }

    @ModelAttribute("isNotSuccessUpdate")
    public boolean addIsNotSuccessUpdate() {
        return false;
    }

    @ModelAttribute("isSuccessUpdate")
    public boolean addIsSuccessUpdate() {
        return false;
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

        return "redirect:/home";
    }

    @GetMapping("/remove/{id}")
    public String removeOrder(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        boolean isSuccessDel = this.orderService.ifReadyDeleteById(id);
        if (!isSuccessDel) {
            redirectAttributes.addFlashAttribute("isNotSuccessDel", true);
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable(name = "id") Long id, Model model) {
        OrderServiceModel osm = this.orderService.findById(id);
        model.addAttribute("orderDetails", osm);
        model.addAttribute("customers",this.customerService.getAllCustomers());
        return "order-details";
    }

    @PreAuthorize("@orderServiceImpl.isOwnerOrAdmin(#userDetails, #id)")
    @DeleteMapping("/delete/{id}")
    public String deleteFromOrderDetails(@PathVariable(name = "id") Long id,
                                         @AuthenticationPrincipal PureWaterUserDetails userDetails) {
        this.orderService.deleteById(id);
        return "redirect:/home";
    }

    @PreAuthorize("@orderServiceImpl.isOwnerOrAdmin(#userDetails, #id)")
    @PatchMapping(value = "/update/{id}")
    public String updateFromOrderDetails(@PathVariable(name = "id") Long id, @AuthenticationPrincipal PureWaterUserDetails userDetails,
                                         @Valid OrderAddBindingModel orderAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isNotSuccessUpdate", true);
            return "redirect:/orders/details/" + id;
        }

        this.orderService.updateOrder(id, orderAddBindingModel);
        //TODO - Change to green
        redirectAttributes.addFlashAttribute("isSuccessUpdate", true);
        return "redirect:/orders/details/" + id;
    }

    @GetMapping("/history")
    public String orderHistory(Model model) {
        model.addAttribute("allOrders", this.orderHistoryService.getAll());
        return "orders-history";
    }
}
