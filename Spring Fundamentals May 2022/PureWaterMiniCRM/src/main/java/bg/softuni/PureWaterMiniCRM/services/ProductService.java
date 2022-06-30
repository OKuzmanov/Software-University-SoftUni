package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;

import java.util.List;

public interface ProductService {
    ProductServiceModel addProducts(ProductServiceModel productServiceModel);

    boolean isRepoEmpty();

    void saveAll(List<Product> products);
}
