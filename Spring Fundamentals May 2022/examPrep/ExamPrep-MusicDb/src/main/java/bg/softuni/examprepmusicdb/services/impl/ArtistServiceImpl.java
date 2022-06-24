package bg.softuni.examprepmusicdb.services.impl;

import bg.softuni.examprepmusicdb.models.entities.Artist;
import bg.softuni.examprepmusicdb.models.enums.ArtistEnum;
import bg.softuni.examprepmusicdb.repositories.ArtistRepository;
import bg.softuni.examprepmusicdb.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepo;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepo) {
        this.artistRepo = artistRepo;
    }

    @Override
    public boolean isRepositoryEmpty() {
        return this.artistRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Artist> artists) {
        this.artistRepo.saveAll(artists);
    }

    @Override
    public Artist fetchByName(ArtistEnum name) {
        return this.artistRepo.findByName(name);
    }
}
