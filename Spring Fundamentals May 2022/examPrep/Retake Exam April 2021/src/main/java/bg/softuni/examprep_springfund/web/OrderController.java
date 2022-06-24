package bg.softuni.examprep_springfund.web;

import bg.softuni.examprep_springfund.model.binding.OrderAddBindingModel;
import bg.softuni.examprep_springfund.model.service.OrderServiceModel;
import bg.softuni.examprep_springfund.service.OrderService;
import bg.softuni.examprep_springfund.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    private String getAddOrder(){

        if(!this.userService.isCurrUserLoggedIn()) {
            return "redirect:/users/login";
        }

        return "order-add";
    }

    @PostMapping("/add")
    private String postAddOrder(@Valid OrderAddBindingModel orderAddBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddBindingModel", orderAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddBindingModel", bindingResult);
            return "redirect:add";
        }

        OrderServiceModel orderServiceModel = this.modelMapper.map(orderAddBindingModel, OrderServiceModel.class);
        this.orderService.addOrder(orderServiceModel);

        return "redirect:/";
    }

    @GetMapping("/ready/{id}")
    public String ready(@PathVariable(name = "id") Long id){
        this.orderService.removeOrder(id);

        return "redirect:/";
    }

    @ModelAttribute
    public OrderAddBindingModel orderAddBindingModel() {
        return new OrderAddBindingModel();
    }

}
