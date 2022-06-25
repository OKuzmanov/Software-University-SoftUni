package bg.softuni.finalexam.services;

import bg.softuni.finalexam.models.entities.Song;
import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.enums.StyleEnum;
import bg.softuni.finalexam.models.serviceModels.SongServiceModel;
import bg.softuni.finalexam.models.viewModels.SongViewModel;
import bg.softuni.finalexam.repositories.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepo;
    private final ModelMapper modelMapper;
    private final StyleService styleService;
    private final UserService userService;

    @Autowired
    public SongServiceImpl(SongRepository songRepo, ModelMapper modelMapper, StyleService styleService, UserService userService) {
        this.songRepo = songRepo;
        this.modelMapper = modelMapper;
        this.styleService = styleService;
        this.userService = userService;
    }

    @Override
    public SongServiceModel findByPerformer(String performer) {
        Optional<Song> songOpt = this.songRepo.findByPerformer(performer);
        return songOpt.isEmpty()
                ? null
                : this.modelMapper.map(songOpt.get(), SongServiceModel.class);
    }

    @Override
    public void addSong(SongServiceModel songServiceModel) {
        Song entityToSave = this.modelMapper.map(songServiceModel, Song.class);
        entityToSave.setStyle(this.styleService.findByStyleName(songServiceModel.getStyle()));
        this.songRepo.save(entityToSave);
    }

    @Override
    public List<SongViewModel> fetchAllByStyle(StyleEnum style) {
        Style popStyle = this.styleService.findByStyleName(style);
        List<Song> allByStyle = this.songRepo.findAllByStyle(popStyle);
        return allByStyle.size() == 0
                ? null
                : allByStyle
                    .stream()
                    .map(s -> this.modelMapper.map(s, SongViewModel.class))
                    .collect(Collectors.toList());
    }

    @Override
    public List<SongViewModel> fetchAllUserSongs() {
        User currentUser = this.userService.getCurrentUser();
        List<Song> allByUserId = this.songRepo.findAllByUserId(currentUser.getId());
        return allByUserId.size() == 0
                ? null
                : allByUserId
                .stream()
                .map(s -> this.modelMapper.map(s, SongViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalUserDuration() {
        User currentUser = this.userService.getCurrentUser();
        List<Song> allByUserId = this.songRepo.findAllByUserId(currentUser.getId());
        return allByUserId.size() == 0
                ? 0
                : allByUserId
                .stream()
                .map(s -> s.getDuration())
                .mapToDouble(s -> s)
                .sum();
    }

    @Override
    public void removeByUserId() {
        User currentUser = this.userService.getCurrentUser();
        currentUser.setPlaylist(new ArrayList<>());
        this.userService.updateUser(currentUser);
    }

    @Override
    public Song findById(Long id) {
        Optional<Song> byId = this.songRepo.findById(id);
        return byId.isEmpty()
                ? null
                : byId.get();
    }
}
