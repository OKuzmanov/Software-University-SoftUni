package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.ProductAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.services.ProductService;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final RawMaterialService rawMaterialService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, UserService userService, SupplierService supplierService, RawMaterialService rawMaterialService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.rawMaterialService = rawMaterialService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("productAddBindingModel")
    public ProductAddBindingModel addBindingModel() {
        return new ProductAddBindingModel();
    }

    @ModelAttribute("insufficientResources")
    public boolean addInsufficientResources() {
        return false;
    }

    @GetMapping("/add")
    public String getAddProducts(){
        return "addProduct";
    }

    @PostMapping("/add")
    public String postAddProducts(@Valid ProductAddBindingModel productAddBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
           redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
           redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

           return "redirect:/products/add";
        }

        if (!this.areThereSufficientResources(productAddBindingModel.getType(), productAddBindingModel.getQuantity())) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("insufficientResources", true);

            return "redirect:/products/add";
        }

        this.productService.addProducts(this.modelMapper.map(productAddBindingModel, ProductServiceModel.class));

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String getAllProducts() {
        return "allProducts";
    }

    private boolean areThereSufficientResources(ProductCategoryEnum type, int quantity) {
        switch (type) {
            case HALF_LITRE:
                int quantityHalfLitreBottle = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLE_HALF_LITRE);
                int quantityHalfLitreCap = this.rawMaterialService.fetchAllByType(RawMaterialType.CAP_HALF_LITRE);
                int quantityBottleneck = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLENECK);
                int quantityLabel = this.rawMaterialService.fetchAllByType(RawMaterialType.LABEL);
                int quantityGlue = this.rawMaterialService.fetchAllByType(RawMaterialType.GLUE);

                if (quantityHalfLitreBottle < quantity || quantityHalfLitreCap < quantity || quantityBottleneck < quantity
                        || quantityLabel < quantity || quantityGlue < quantity) {
                    return false;
                } else {
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLE_HALF_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.CAP_HALF_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLENECK, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.LABEL, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.GLUE, quantity);
                    return true;
                }
            case LITRE_AND_HALF:
                int quantityLitreAndHalfBottle = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLE_LITRE_AND_HALF);
                int quantityLitreAndHalfCap = this.rawMaterialService.fetchAllByType(RawMaterialType.CAP_LITRE_AND_HALF);
                int quantityLitreAndHalfBottleneck = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLENECK);
                int quantityLitreAndHalfLabel = this.rawMaterialService.fetchAllByType(RawMaterialType.LABEL);
                int quantityLitreAndHalfGlue = this.rawMaterialService.fetchAllByType(RawMaterialType.GLUE);

                if (quantityLitreAndHalfBottle < quantity || quantityLitreAndHalfCap < quantity
                        || quantityLitreAndHalfBottleneck < quantity || quantityLitreAndHalfLabel < quantity
                        || quantityLitreAndHalfGlue < quantity) {

                    return false;
                } else {
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLE_LITRE_AND_HALF, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.CAP_LITRE_AND_HALF, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLENECK, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.LABEL, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.GLUE, quantity);

                    return true;
                }
            case TEN_LITRES:
                int quantityTenLitreBottle = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLE_TEN_LITRE);
                int quantityTenLitreCap = this.rawMaterialService.fetchAllByType(RawMaterialType.CAP_TEN_LITRE);
                int quantityTenLitreBottleneck = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLENECK);
                int quantityTenLitreHandle = this.rawMaterialService.fetchAllByType(RawMaterialType.HANDLE_TEN_LITRES);
                int quantityTenLitreLabel = this.rawMaterialService.fetchAllByType(RawMaterialType.LABEL);
                int quantityTenLitreGlue = this.rawMaterialService.fetchAllByType(RawMaterialType.GLUE);

                if (quantityTenLitreBottle < quantity || quantityTenLitreCap < quantity || quantityTenLitreBottleneck < quantity
                        || quantityTenLitreHandle < quantity || quantityTenLitreLabel < quantity || quantityTenLitreGlue < quantity) {
                    return false;
                } else {
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLE_TEN_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.CAP_TEN_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLENECK, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.HANDLE_TEN_LITRES, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.LABEL, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.GLUE, quantity);

                    return true;
                }
            case NINETEEN_LITRES:
                int quantityNineteenLitreBottle = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLE_NINETEEN_LITRE);
                int quantityNineteenLitreCap = this.rawMaterialService.fetchAllByType(RawMaterialType.CAP_NINETEEN_LITRE);
                int quantityNineteenLitreBottleneck = this.rawMaterialService.fetchAllByType(RawMaterialType.BOTTLENECK);
                int quantityNineteenLitreLabel = this.rawMaterialService.fetchAllByType(RawMaterialType.LABEL);
                int quantityNineteenLitreGlue = this.rawMaterialService.fetchAllByType(RawMaterialType.GLUE);

                if (quantityNineteenLitreBottle < quantity || quantityNineteenLitreCap < quantity || quantityNineteenLitreBottleneck < quantity
                        || quantityNineteenLitreLabel < quantity || quantityNineteenLitreGlue < quantity) {
                    return false;
                } else {
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLE_NINETEEN_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.CAP_NINETEEN_LITRE, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.BOTTLENECK, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.LABEL, quantity);
                    this.rawMaterialService.reduceQuantityBy(RawMaterialType.GLUE, quantity);

                    return true;
                }
            default:
                return false;
        }
    }
}
