package bg.softuni.examprepshoppinglist.web;

import bg.softuni.examprepshoppinglist.models.bindingModels.AddProductBindingModel;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;
import bg.softuni.examprepshoppinglist.services.ProductService;
import bg.softuni.examprepshoppinglist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper, UserService userService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
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
        if (this.userService.getCurrentUser().getUsername() == null) {
            return "redirect:/users/login";
        }
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

    @GetMapping("/buy/{id}")
    public String getBuy(@PathVariable(name = "id") Long id) {

        this.productService.removeProductById(id);

        return "redirect:/home";
    }

    @GetMapping("/buy/all")
    public String getBuy() {

        this.productService.removeAll();

        return "redirect:/home";
    }
}
