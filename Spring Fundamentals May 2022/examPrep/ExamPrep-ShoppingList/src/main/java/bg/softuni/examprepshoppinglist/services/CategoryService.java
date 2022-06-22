package bg.softuni.examprepshoppinglist.services;

import bg.softuni.examprepshoppinglist.models.entities.Category;
import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;

import java.util.List;

public interface CategoryService {

    boolean isCategoryRepoEmpty();

    void saveAll(List<Category> categories);

    Category findByName(CategoryEnum name);
}
