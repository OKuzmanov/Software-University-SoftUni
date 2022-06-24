package bg.softuni.examprepmusicdb.web;

import bg.softuni.examprepmusicdb.models.bindingModels.AddAlbumBindingModel;
import bg.softuni.examprepmusicdb.models.serviceModels.AlbumServiceModel;
import bg.softuni.examprepmusicdb.services.AlbumService;
import bg.softuni.examprepmusicdb.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public AlbumController(AlbumService albumService, ModelMapper modelMapper, UserService userService) {
        this.albumService = albumService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("addAlbumBindingModel")
    public AddAlbumBindingModel AddAlbumBindingModel() {
        return new AddAlbumBindingModel();
    }

    @GetMapping("/add")
    public String getAddAlbum() {
        return "add-album";
    }

    @PostMapping("/add")
    public String postAddAlbum(@Valid AddAlbumBindingModel addAlbumBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (this.userService.isCurrentUserLoggedIn()) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addAlbumBindingModel", addAlbumBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addAlbumBindingModel", bindingResult);
            return "redirect:/albums/add";
        }

        this.albumService.addAlbum(this.modelMapper.map(addAlbumBindingModel, AlbumServiceModel.class));

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String removeAlbum(@PathVariable Long id) {

        this.albumService.removeAlbum(id);

        return "redirect:/home";
    }
}
