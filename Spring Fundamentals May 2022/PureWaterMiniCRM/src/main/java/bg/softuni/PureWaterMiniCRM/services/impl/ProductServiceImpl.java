package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.ProductViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.ProductRepository;
import bg.softuni.PureWaterMiniCRM.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<ProductViewModel> fetchAll() {
        List<ProductViewModel> models = this.productRepo.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());

        return models;
    }

    @Override
    public ProductViewModel fetchById(Long id) {
        Optional<Product> productOpt = this.productRepo.findById(id);

        ProductViewModel pvm = productOpt.map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .orElseThrow(() -> new ApiObjectNotFoundException(id, "Product"));

        return pvm;
    }

    @Override
    public Integer findQuantityProducedOfType(ProductCategoryEnum type) {
        Product productEntity = this.productRepo.findByType(type).get();
        return productEntity.getQuantity();
    }

    @Override
    public void reduceQuantityBy(ProductCategoryEnum type, int orderQuantity) {
        Product productEntity = this.productRepo.findByType(type).get();
        productEntity.setQuantity(productEntity.getQuantity() - orderQuantity);
        this.productRepo.save(productEntity);
    }

    @Override
    public List<ProductServiceModel> findAll() {
        return this.productRepo
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .toList();
    }

    @Override
    public ProductServiceModel findById(long id) {
        Product productEntity = this.productRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Product"));
        return this.modelMapper.map(productEntity, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel updateProduct(Long id, ProductServiceModel productServiceModel) {
        Product productEntity = this.productRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Product"));

        productEntity.setType(productServiceModel.getType());
        productEntity.setQuantity(productServiceModel.getQuantity());
        productEntity.setProductionDate(productServiceModel.getProductionDate());

        Product savedEntity = this.productRepo.save(productEntity);

        return this.modelMapper.map(savedEntity, ProductServiceModel.class);
    }

    @Override
    public boolean isAdmin(PureWaterUserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + RoleEnum.ADMIN));
    }

    @Override
    public void deleteProduct(long id) {
        Product productEntity = this.productRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Product"));

        this.productRepo.delete(productEntity);
    }
}
