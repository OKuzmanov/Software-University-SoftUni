package bg.softuni.examprepshoppinglist.services;

import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductServiceModel findByName(String name);

    boolean addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> fetchAllByCategory(CategoryEnum category);

    void removeProductById(Long id);

    Double fetchTotalProductsPrice();

    void removeAll();
}
