package bg.softuni.examprepmusicdb.services;

import bg.softuni.examprepmusicdb.models.entities.Artist;
import bg.softuni.examprepmusicdb.models.enums.ArtistEnum;

import java.util.List;

public interface ArtistService {
    boolean isRepositoryEmpty();

    void saveAll(List<Artist> artists);

    Artist fetchByName(ArtistEnum name);
}
