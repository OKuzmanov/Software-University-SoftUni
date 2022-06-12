package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.RawMaterialAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/materials")
public class RawMaterialsController {

    private final RawMaterialService rawMaterialService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final SupplierService supplierService;

    @Autowired
    public RawMaterialsController(RawMaterialService rawMaterialService, ModelMapper modelMapper, UserService userService, SupplierService supplierService) {
        this.rawMaterialService = rawMaterialService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.supplierService = supplierService;
    }

    @GetMapping("/add")
    public String getAddRawMaterials(Model model) {
        model.addAttribute("suppliers", this.supplierService.findAllSuppliers());
        return "addRawMaterial";
    }

    @PostMapping("/add")
    public String postAddRawMaterials(@Valid RawMaterialAddBindingModel rawMaterialAddBindingModel, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("rawMaterialAddBindingModel", rawMaterialAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.rawMaterialAddBindingModel", bindingResult);

            return "redirect:/materials/add";
        }

        RawMaterialServiceModel rmsm = this.modelMapper.map(rawMaterialAddBindingModel, RawMaterialServiceModel.class);
        rmsm.setSupplier(this.supplierService.findByCompanyName(rawMaterialAddBindingModel.getSupplierName()));
        rmsm.setUser(this.userService.getCurrentUser());

        this.rawMaterialService.addRawMaterials(rmsm);

        return "allRawMaterials";
    }

    @ModelAttribute("rawMaterialAddBindingModel")
    public RawMaterialAddBindingModel addBindingModel() {
        return new RawMaterialAddBindingModel();
    }
}
