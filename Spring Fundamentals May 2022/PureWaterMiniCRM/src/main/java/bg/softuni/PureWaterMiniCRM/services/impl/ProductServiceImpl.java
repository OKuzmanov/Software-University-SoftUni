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

        if (this.productRepo.findAll().size() == 0) {
            this.dataBaseInit();
        }

        Product pEntity = this.productRepo.findByType(productServiceModel.getType()).get();

        pEntity.setQuantity(pEntity.getQuantity() + productServiceModel.getQuantity());
        pEntity.setProductionDate(productServiceModel.getProductionDate());

        this.productRepo.save(pEntity);

        return this.modelMapper.map(pEntity, ProductServiceModel.class);
    }

    private void dataBaseInit() {
        Product p1 = new Product(0, ProductCategoryEnum.HALF_LITRE, LocalDateTime.now());
        Product p2 = new Product(0, ProductCategoryEnum.LITRE_AND_HALF, LocalDateTime.now());
        Product p3 = new Product(0, ProductCategoryEnum.TEN_LITRES, LocalDateTime.now());
        Product p4 = new Product(0, ProductCategoryEnum.NINETEEN_LITRES, LocalDateTime.now());

        this.productRepo.saveAll(List.of(p1,p2,p3,p4));
    }
}
