package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.CustomerAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String getAllCustomers() {
        return "allCustomers";
    }
}
