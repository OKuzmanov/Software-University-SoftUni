package bg.softuni.examprepbattleships;

import bg.softuni.examprepbattleships.models.entities.Category;
import bg.softuni.examprepbattleships.models.enums.CategoryEnum;
import bg.softuni.examprepbattleships.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private CategoryRepository categoryRepo;

    @Autowired
    public ApplicationRunner(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        this.databaseInit();
    }

    private void databaseInit() {
        if (this.categoryRepo.findAll().size() == 0) {
            Category catBattleShip = new Category(CategoryEnum.BATTLE, "This space ship is used in military combat.");
            Category catTransportShip = new Category(CategoryEnum.CARGO, "This spaceship is used for transport of peaople, goods and life stock.");
            Category catPatrolShip = new Category(CategoryEnum.PATROL, "This spaceship is used to protect to fight off enemy spaceships.");

            this.categoryRepo.saveAll(List.of(catBattleShip, catTransportShip, catPatrolShip));
        }
    }
}
