package bg.softuni.examprepmusicdb.services;

import bg.softuni.examprepmusicdb.models.entities.Album;
import bg.softuni.examprepmusicdb.models.serviceModels.AlbumServiceModel;

import java.util.List;

public interface AlbumService {
    void addAlbum(AlbumServiceModel albumServiceModel);

    List<Album> fetchAllAlbums();

    void removeAlbum(Long id);
}
