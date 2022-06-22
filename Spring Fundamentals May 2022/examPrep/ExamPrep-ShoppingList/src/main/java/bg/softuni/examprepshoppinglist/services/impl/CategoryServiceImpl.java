package bg.softuni.examprepshoppinglist.services.impl;

import bg.softuni.examprepshoppinglist.models.entities.Category;
import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import bg.softuni.examprepshoppinglist.repositories.CategoryRepository;
import bg.softuni.examprepshoppinglist.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public boolean isCategoryRepoEmpty() {
        return this.categoryRepo.findAll().size() == 0;
    }

    @Override
    public void saveAll(List<Category> categories) {
        this.categoryRepo.saveAll(categories);
    }

    @Override
    public Category findByName(CategoryEnum name) {
        Optional<Category> optCategory = this.categoryRepo.findByName(name);
        return optCategory.isEmpty()
                ? null
                : optCategory.get();
    }
}
