package bg.softuni.finalexam.web;

import bg.softuni.finalexam.models.bindingModels.SongsAddBindingModel;
import bg.softuni.finalexam.models.entities.Song;
import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.serviceModels.SongServiceModel;
import bg.softuni.finalexam.services.SongService;
import bg.softuni.finalexam.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongsController {

    private final SongService songService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public SongsController(SongService songService, ModelMapper modelMapper, UserService userService) {
        this.songService = songService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("isPerformerExists")
    public boolean addisPerformerExists() {
        return false;
    }

    @ModelAttribute("songsAddBindingModel")
    public SongsAddBindingModel addsongsAddBindingModel() {
        return new SongsAddBindingModel();
    }

    @GetMapping("/add")
    public String getAddSongs() {
        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }
        return "song-add";
    }

    @PostMapping("/add")
    public String postAddSongs(@Valid SongsAddBindingModel songsAddBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("songsAddBindingModel", songsAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.songsAddBindingModel", bindingResult);
            return "redirect:/songs/add";
        }

        SongServiceModel ssmCheckPerformer = this.songService.findByPerformer(songsAddBindingModel.getPerformer());
        if(ssmCheckPerformer != null) {
            redirectAttributes.addFlashAttribute("songsAddBindingModel", songsAddBindingModel);
            redirectAttributes.addFlashAttribute("isPerformerExists", true);
            return "redirect:/songs/add";
        }

        this.songService.addSong(this.modelMapper.map(songsAddBindingModel, SongServiceModel.class));

        return "redirect:/home";
    }

    @GetMapping("/remove/all")
    public String removeSongs() {
        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }
        this.songService.removeByUserId();
        return "redirect:/home";
    }

    @GetMapping("/add/{id}")
    public String removeSongs(@PathVariable Long id) {
        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        Song song = this.songService.findById(id);

        User currentUser = this.userService.getCurrentUser();
        List<Song> playlist = currentUser.getPlaylist();
        playlist.add(song);
        currentUser.setPlaylist(playlist);
        this.userService.updateUser(currentUser);
        return "redirect:/home";
    }
}
