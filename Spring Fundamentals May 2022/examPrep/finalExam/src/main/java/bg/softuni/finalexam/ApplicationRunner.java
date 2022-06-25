package bg.softuni.finalexam;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.enums.StyleEnum;
import bg.softuni.finalexam.repositories.StyleRepository;
import bg.softuni.finalexam.services.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final StyleService styleService;

    @Autowired
    public ApplicationRunner(StyleService styleService) {
        this.styleService = styleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.dbInit();
    }

    private void dbInit() {
        if (this.styleService.checkIsRepoEmpty()) {
            Style s1 = new Style(StyleEnum.POP, "This songs are mainly in the pop genre.");
            Style s2 = new Style(StyleEnum.JAZZ, "This songs are mainly in the jazz genre.");
            Style s3 = new Style(StyleEnum.ROCK, "This songs are mainly in the rock genre.");

            this.styleService.saveAll(List.of(s1, s2, s3));
        }
    }
}
