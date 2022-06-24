package bg.softuni.examprepmusicdb.services.impl;

import bg.softuni.examprepmusicdb.models.entities.Album;
import bg.softuni.examprepmusicdb.models.serviceModels.AlbumServiceModel;
import bg.softuni.examprepmusicdb.repositories.AlbumRepository;
import bg.softuni.examprepmusicdb.services.AlbumService;
import bg.softuni.examprepmusicdb.services.ArtistService;
import bg.softuni.examprepmusicdb.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ArtistService artistService;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepo, ModelMapper modelMapper, UserService userService, ArtistService artistService) {
        this.albumRepo = albumRepo;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.artistService = artistService;
    }

    @Override
    public void addAlbum(AlbumServiceModel albumServiceModel) {
        Album entityToSave = this.modelMapper.map(albumServiceModel, Album.class);

        entityToSave.setAddedFrom(userService.fetchCurrentUser());
        entityToSave.setArtist(this.artistService.fetchByName(albumServiceModel.getArtist()));

        this.albumRepo.save(entityToSave);
    }

    @Override
    public List<Album> fetchAllAlbums() {
        List<Album> all = this.albumRepo.findAll();
        return all;
    }

    @Override
    public void removeAlbum(Long id) {
        this.albumRepo.deleteById(id);
    }
}
