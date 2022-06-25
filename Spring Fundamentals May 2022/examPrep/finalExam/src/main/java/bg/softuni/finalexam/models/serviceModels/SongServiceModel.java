package bg.softuni.finalexam.models.serviceModels;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.enums.StyleEnum;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

public class SongServiceModel {

    private String performer;

    private String title;

    private double duration;

    private LocalDate releaseDate;

    private StyleEnum style;

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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }
}
