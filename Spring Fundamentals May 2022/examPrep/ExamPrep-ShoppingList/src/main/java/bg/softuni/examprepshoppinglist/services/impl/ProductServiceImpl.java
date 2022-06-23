package bg.softuni.examprepshoppinglist.services.impl;

import bg.softuni.examprepshoppinglist.models.entities.Category;
import bg.softuni.examprepshoppinglist.models.entities.Product;
import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;
import bg.softuni.examprepshoppinglist.repositories.ProductRepository;
import bg.softuni.examprepshoppinglist.services.CategoryService;
import bg.softuni.examprepshoppinglist.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ProductServiceModel findByName(String name) {
        Optional<Product> optProduct = this.productRepo.findByName(name);
        return optProduct.isEmpty()
                ? null
                : this.modelMapper.map(optProduct.get(), ProductServiceModel.class);
    }

    @Override
    public boolean addProduct(ProductServiceModel productServiceModel) {
        Product entityToSave = this.modelMapper.map(productServiceModel, Product.class);
        entityToSave.setCategory(this.categoryService.findByName(productServiceModel.getCategory()));
        this.productRepo.save(entityToSave);
        return entityToSave.getId() != null;
    }

    @Override
    public List<ProductServiceModel> fetchAllByCategory(CategoryEnum category) {
        Category categ = this.categoryService.findByName(category);
        List<Product> allByCategory = this.productRepo.findAllByCategory(categ);
        return allByCategory.isEmpty()
                ? null
                : allByCategory
                    .stream()
                    .map( p -> this.modelMapper.map(p, ProductServiceModel.class))
                    .collect(Collectors.toList());
    }

    @Override
    public void removeProductById(Long id) {
        this.productRepo.deleteById(id);
    }

    @Override
    public Double fetchTotalProductsPrice() {
        List<Product> all = this.productRepo.findAll();
        return all.stream().map(p -> p.getPrice()).mapToDouble(BigDecimal -> BigDecimal.doubleValue()).sum();
    }

    @Override
    public void removeAll() {
        this.productRepo.deleteAll();
    }
}
