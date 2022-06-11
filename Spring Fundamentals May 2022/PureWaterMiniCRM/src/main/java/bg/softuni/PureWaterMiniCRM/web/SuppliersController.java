package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.SupplierAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/suppliers")
public class SuppliersController {

    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    public SuppliersController(SupplierService supplierService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String getAddSupplier() {

        return "addSupplier";
    }

    @PostMapping("/add")
    public String postAddSupplier(@Valid SupplierAddBindingModel supplierAddBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("supplierAddBindingModel", supplierAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplierAddBindingModel", bindingResult);

            return "redirect:/suppliers/add";
        }

        SupplierServiceModel ssmCheckExists = this.supplierService.findByCompanyName(supplierAddBindingModel.getCompanyName());
        if (ssmCheckExists != null) {
            redirectAttributes.addFlashAttribute("supplierAddBindingModel", supplierAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplierAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isExist", true);

            return "redirect:/suppliers/add";
        }

        SupplierServiceModel ssm = this.modelMapper.map(supplierAddBindingModel, SupplierServiceModel.class);

        this.supplierService.addSupplier(ssm);

        return "allSuppliers";
    }

    @ModelAttribute("supplierAddBindingModel")
    public SupplierAddBindingModel addBindingModel() {
        return new SupplierAddBindingModel();
    }

    @ModelAttribute("isExist")
    public boolean addIsExist() {
        return false;
    }
}
