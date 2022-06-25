package bg.softuni.finalexam.services;

import bg.softuni.finalexam.models.entities.Song;
import bg.softuni.finalexam.models.enums.StyleEnum;
import bg.softuni.finalexam.models.serviceModels.SongServiceModel;
import bg.softuni.finalexam.models.viewModels.SongViewModel;

import java.util.List;

public interface SongService {
    SongServiceModel findByPerformer(String performer);

    void addSong(SongServiceModel songServiceModel);

    List<SongViewModel> fetchAllByStyle(StyleEnum style);

    List<SongViewModel> fetchAllUserSongs();

    double getTotalUserDuration();

    void removeByUserId();

    Song findById(Long id);
}
