package bg.softuni.finalexam.services.impl;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.enums.StyleEnum;
import bg.softuni.finalexam.repositories.StyleRepository;
import bg.softuni.finalexam.services.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StyleServiceImpl implements StyleService {

    private final StyleRepository styleRepo;

    @Autowired
    public StyleServiceImpl(StyleRepository styleRepo) {
        this.styleRepo = styleRepo;
    }

    @Override
    public boolean checkIsRepoEmpty() {
        return this.styleRepo.findAll().size() == 0;
    }

    @Override
    public void saveAll(List<Style> styles) {
        this.styleRepo.saveAll(styles);
    }

    @Override
    public Style findByStyleName(StyleEnum style) {
        Optional<Style> styleOpt = this.styleRepo.findByStyleName(style);
        return styleOpt.isEmpty()
                ? null
                : styleOpt.get();
    }
}
