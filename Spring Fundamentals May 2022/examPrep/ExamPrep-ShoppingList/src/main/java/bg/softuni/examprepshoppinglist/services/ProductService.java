package bg.softuni.examprepshoppinglist.services;

import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;

public interface ProductService {
    ProductServiceModel findByName(String name);

    boolean addProduct(ProductServiceModel productServiceModel);
}
