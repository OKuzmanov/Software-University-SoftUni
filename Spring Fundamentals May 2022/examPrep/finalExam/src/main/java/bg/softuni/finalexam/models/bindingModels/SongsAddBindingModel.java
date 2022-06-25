package bg.softuni.finalexam.models.bindingModels;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.enums.StyleEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class SongsAddBindingModel {

    @NotBlank
    @Size(min = 3, max = 20)
    private String performer;

    @NotBlank
    @Size(min = 2, max = 20)
    private String title;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive
    private double duration;

    @NotNull
    private StyleEnum style;

    public SongsAddBindingModel() {
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }
}
