package bg.softuni.examprepshoppinglist;

import bg.softuni.examprepshoppinglist.models.entities.Category;
import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import bg.softuni.examprepshoppinglist.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final CategoryService categoryService;

    @Autowired
    public ApplicationRunner(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.dbInit();
    }

    private void dbInit() {
        if (this.categoryService.isCategoryRepoEmpty()) {
            Category cat1 = new Category(CategoryEnum.FOOD, "Used for items such as bread, eggs, milk, etc.");
            Category cat2 = new Category(CategoryEnum.DRINK, "Used for items such as water, tea, coffee, etc.");
            Category cat3 = new Category(CategoryEnum.HOUSEHOLD, "Used for items such as toilet paper, kitchen paper, bin bags, etc.");
            Category cat4 = new Category(CategoryEnum.OTHER, "Used for items not fitted for the categories above.");

            this.categoryService.saveAll(List.of(cat1, cat2, cat3, cat4));
        }
    }
}
