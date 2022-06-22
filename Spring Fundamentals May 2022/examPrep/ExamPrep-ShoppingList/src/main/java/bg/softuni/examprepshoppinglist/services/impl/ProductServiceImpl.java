package bg.softuni.examprepshoppinglist.services.impl;

import bg.softuni.examprepshoppinglist.models.entities.Product;
import bg.softuni.examprepshoppinglist.models.services.ProductServiceModel;
import bg.softuni.examprepshoppinglist.repositories.ProductRepository;
import bg.softuni.examprepshoppinglist.services.CategoryService;
import bg.softuni.examprepshoppinglist.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
