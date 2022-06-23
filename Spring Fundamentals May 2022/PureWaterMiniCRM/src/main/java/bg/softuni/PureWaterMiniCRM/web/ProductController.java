package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.ProductAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.services.ProductService;
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

        this.productService.addProducts(this.modelMapper.map(productAddBindingModel, ProductServiceModel.class));

        return "allProducts";
    }

    @ModelAttribute("productAddBindingModel")
    public ProductAddBindingModel addBindingModel() {
        return new ProductAddBindingModel();
    }
}