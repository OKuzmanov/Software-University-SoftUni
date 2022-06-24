package bg.softuni.examprepmusicdb.web;

import bg.softuni.examprepmusicdb.models.entities.Album;
import bg.softuni.examprepmusicdb.services.AlbumService;
import bg.softuni.examprepmusicdb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AlbumService albumService;
    private final UserService userService;

    @Autowired
    public HomeController(AlbumService albumService, UserService userService) {
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        if (this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/users/login";
        }

        List<Album> albumList = this.albumService.fetchAllAlbums();

        model.addAttribute("albumList", albumList);
        model.addAttribute("allCopies", albumList
                .stream()
                .map(Album::getCopies)
                .mapToInt(e -> e)
                .sum());

        return "home";
    }
}
