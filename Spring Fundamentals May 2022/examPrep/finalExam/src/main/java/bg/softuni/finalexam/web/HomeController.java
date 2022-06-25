package bg.softuni.finalexam.web;

import bg.softuni.finalexam.models.enums.StyleEnum;
import bg.softuni.finalexam.models.viewModels.SongViewModel;
import bg.softuni.finalexam.services.SongService;
import bg.softuni.finalexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final SongService songService;
    private final UserService userService;

    @Autowired
    public HomeController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        if (this.userService.getCurrentUser() != null) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }
        List<SongViewModel> popViews = this.songService.fetchAllByStyle(StyleEnum.POP);
        List<SongViewModel> rockViews = this.songService.fetchAllByStyle(StyleEnum.ROCK);
        List<SongViewModel> jazzViews = this.songService.fetchAllByStyle(StyleEnum.JAZZ);

        model.addAttribute("popViews", popViews);
        model.addAttribute("rockViews", rockViews);
        model.addAttribute("jazzViews", jazzViews);

        List<SongViewModel> userSongs = this.songService.fetchAllUserSongs();
        model.addAttribute("userSongs", userSongs);

        double totalUserDuration = this.songService.getTotalUserDuration();
        model.addAttribute("totalUserDuration", totalUserDuration);

        return "home";
    }
}
