package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.ProductRepository;
import bg.softuni.PureWaterMiniCRM.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepo, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProducts(ProductServiceModel productServiceModel) {

        Product pEntity = this.productRepo.findByType(productServiceModel.getType()).get();

        pEntity.setQuantity(pEntity.getQuantity() + productServiceModel.getQuantity());
        pEntity.setProductionDate(productServiceModel.getProductionDate());

        this.productRepo.save(pEntity);

        return this.modelMapper.map(pEntity, ProductServiceModel.class);
    }

    @Override
    public boolean isRepoEmpty() {
        return this.productRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Product> products) {
        this.productRepo.saveAll(products);
    }
}
