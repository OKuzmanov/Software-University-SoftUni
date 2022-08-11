package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.ProductAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.bindingModels.RawMaterialAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.bindingModels.RawMaterialUpdateBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
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

    @ModelAttribute("rawMaterialAddBindingModel")
    public RawMaterialAddBindingModel addBindingModel() {
        return new RawMaterialAddBindingModel();
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

        this.rawMaterialService.addRawMaterials(rmsm);

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String getAllSupps(Model model) {
        List<RawMaterialServiceModel> allMaterials = this.rawMaterialService.findAll();
        model.addAttribute("allMaterials", allMaterials);
        return "allRawMaterials";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable(name = "id") Long id, Model model) {
        RawMaterialServiceModel rmsm = this.rawMaterialService.findById(id.intValue());
        model.addAttribute("materialDetails", rmsm);

        List<SupplierServiceModel> allSuppliers = supplierService.findAllSuppliers();
        model.addAttribute("allSuppliers", allSuppliers);

        return "material-details";
    }

    @PreAuthorize("@rawMaterialServiceImpl.isAdmin(#userDetails)")
    @PostMapping("/update/{id}")
    public String rawMaterialsUpdate(@PathVariable(name = "id") Long id,
                                @Valid RawMaterialUpdateBindingModel rawMaterialUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isNotUpdateSuccess", true);
            return "redirect:/materials/details/" + id;
        }

        RawMaterialServiceModel rmsm = this.modelMapper.map(rawMaterialUpdateBindingModel, RawMaterialServiceModel.class);
        rmsm.setSupplier(this.supplierService.getSupplierByCompanyName(rawMaterialUpdateBindingModel.getSupplier()));
        this.rawMaterialService.updateRawMaterial(id, rmsm);

        redirectAttributes.addFlashAttribute("isUpdateSuccess", true);
        return "redirect:/materials/details/" + id;
    }

    @PreAuthorize("@rawMaterialServiceImpl.isAdmin(#userDetails)")
    @DeleteMapping("/delete/{id}")
    public String deleteRawMaterial(@PathVariable(name = "id") Long id,
                              @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        this.rawMaterialService.deleteRawMaterial(id);

        return "redirect:/materials/all";
    }
}
