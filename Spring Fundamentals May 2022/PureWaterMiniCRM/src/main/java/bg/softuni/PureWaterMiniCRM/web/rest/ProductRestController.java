package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.viewModels.*;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductViewModel>> getAllProducts() {
        return ResponseEntity
                .ok(this.productService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewModel> getProductById(@PathVariable Long id) {

        ProductViewModel pvm = this.productService.fetchById(id);

        return ResponseEntity.ok(pvm);

    }
}
