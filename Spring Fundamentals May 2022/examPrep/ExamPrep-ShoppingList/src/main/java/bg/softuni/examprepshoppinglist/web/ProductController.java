package bg.softuni.examprepshoppinglist.web;

import bg.softuni.examprepshoppinglist.models.bindingModels.AddProductBindingModel;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;
import bg.softuni.examprepshoppinglist.services.ProductService;
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
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("isNameTaken")
    public boolean addIsNametaken() {
        return false;
    }

    @ModelAttribute("addProductBindingModel")
    public AddProductBindingModel addAddProductBindingModel() {
        return new AddProductBindingModel();
    }

    @GetMapping("/add")
    public String getAddProduct() {
        return "product-add";
    }

    @PostMapping("/add")
    public String postAddProduct(@Valid AddProductBindingModel addProductBindingModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductBindingModel", addProductBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductBindingModel", bindingResult);
            return "redirect:/products/add";
        }

        ProductServiceModel psm = this.productService.findByName(addProductBindingModel.getName());

        if (psm != null) {
            redirectAttributes.addFlashAttribute("addProductBindingModel", addProductBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isNameTaken", true);
            return "redirect:/products/add";
        }

        this.productService.addProduct(this.modelMapper.map(addProductBindingModel, ProductServiceModel.class));

        return "redirect:/home";
    }
}
