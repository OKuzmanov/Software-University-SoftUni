package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.CustomerAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.bindingModels.UserUpdateBindingModel;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, UserService userService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("customerAddBindingModel")
    public CustomerAddBindingModel customerAddBindingModel() {
        return new CustomerAddBindingModel();
    }

    @ModelAttribute("isExist")
    public boolean addIsExist() {
        return false;
    }

    @ModelAttribute(name = "isNotUpdateSuccess")
    public boolean addIsNotUpdateSuccess() {
        return false;
    }

    @ModelAttribute(name = "isUpdateSuccess")
    public boolean addIsUpdateSuccess() {
        return false;
    }

    @GetMapping("/add")
    public String getAddCustomer() {
      return "addCustomer";
    }

    @PostMapping("/add")
    public String postAddCustomer(@Valid CustomerAddBindingModel customerAddBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, @AuthenticationPrincipal PureWaterUserDetails userDetails){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("customerAddBindingModel", customerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerAddBindingModel", bindingResult);

            return "redirect:/customers/add";
        }

        CustomerServiceModel csmCompanyNameCheck = this.customerService.findCustomerByCompanyName(customerAddBindingModel.getCompanyName());

        if(csmCompanyNameCheck != null) {
            redirectAttributes.addFlashAttribute("customerAddBindingModel", customerAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isExist", true);

            return "redirect:/customers/add";
        }

        this.customerService.addCustomer(this.modelMapper.map(customerAddBindingModel, CustomerServiceModel.class), userDetails);

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        List<Customer> allCustomers = this.customerService.findAll();
        model.addAttribute("allCustomers", allCustomers);
        return "allCustomers";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable(name = "id") Long id, Model model) {
        Customer customer = this.customerService.findById(id.intValue());
        model.addAttribute("customerDetails", customer);
        return "customer-details";
    }

    @PreAuthorize("@customerServiceImpl.isOwnerOrAdmin(#userDetails, #id)")
    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable(name = "id") Long id,
                             @Valid CustomerAddBindingModel customerAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isNotUpdateSuccess", true);
            return "redirect:/customers/details/" + id;
        }

        this.customerService.updateCustomer(id, this.modelMapper.map(customerAddBindingModel, CustomerServiceModel.class));

        redirectAttributes.addFlashAttribute("isUpdateSuccess", true);
        return "redirect:/customers/details/" + id;
    }

    @PreAuthorize("@customerServiceImpl.isOwnerOrAdmin(#userDetails, #id)")
    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id,
                             @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        this.customerService.deleteCustomer(id);

        return "redirect:/customers/all";
    }
}
