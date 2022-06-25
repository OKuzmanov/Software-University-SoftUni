package bg.softuni.finalexam.models.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String performer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double duration;

    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "style_id", referencedColumnName = "id", nullable = false)
    private Style style;

    @ManyToMany(mappedBy = "playlist", targetEntity = User.class)
    private List<User> users;

    public Song() {
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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
