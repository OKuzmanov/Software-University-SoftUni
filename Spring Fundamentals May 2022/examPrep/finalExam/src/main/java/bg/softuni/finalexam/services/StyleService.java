package bg.softuni.finalexam.services;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.enums.StyleEnum;

import java.util.List;

public interface StyleService {
    boolean checkIsRepoEmpty();

    void saveAll(List<Style> styles);

    Style findByStyleName(StyleEnum style);
}
