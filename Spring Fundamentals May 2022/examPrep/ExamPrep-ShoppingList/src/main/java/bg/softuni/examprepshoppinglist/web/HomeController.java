package bg.softuni.examprepshoppinglist.web;

import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;
import bg.softuni.examprepshoppinglist.services.ProductService;
import bg.softuni.examprepshoppinglist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public HomeController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        if (this.userService.getCurrentUser().getUsername() == null) {
            return "redirect:/users/login";
        }

        List<ProductServiceModel> foods = this.productService.fetchAllByCategory(CategoryEnum.FOOD);
        List<ProductServiceModel> drinks = this.productService.fetchAllByCategory(CategoryEnum.DRINK);
        List<ProductServiceModel> household = this.productService.fetchAllByCategory(CategoryEnum.HOUSEHOLD);
        List<ProductServiceModel> other = this.productService.fetchAllByCategory(CategoryEnum.OTHER);
        Double totalSum = this.productService.fetchTotalProductsPrice();

        model.addAttribute("foods", foods);
        model.addAttribute("drinks", drinks);
        model.addAttribute("household", household);
        model.addAttribute("other", other);
        model.addAttribute("totalSum", String.format("%.2f",totalSum));

        return "home";
    }
}
