package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.ProductViewModel;

import java.util.List;

public interface ProductService {
    ProductServiceModel addProducts(ProductServiceModel productServiceModel);

    boolean isRepoEmpty();

    void saveAll(List<Product> products);

    List<ProductViewModel> fetchAll();

    ProductViewModel fetchById(Long id);

    Integer findQuantityProducedOfType(ProductCategoryEnum type);

    void reduceQuantityBy(ProductCategoryEnum type, int orderQuantity);
}
