package bg.softuni.examprepmusicdb.models.entities;

import bg.softuni.examprepmusicdb.models.enums.ArtistEnum;

import javax.persistence.*;

@Entity
@Table(name = "artists")
public class Artist extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private ArtistEnum name;

    @Column(name = "career_information", columnDefinition = "text")
    private String careerInformation;

    public Artist() {
    }

    public Artist(ArtistEnum name, String careerInformation) {
        this.name = name;
        this.careerInformation = careerInformation;
    }

    public ArtistEnum getName() {
        return name;
    }

    public void setName(ArtistEnum name) {
        this.name = name;
    }

    public String getCareerInformation() {
        return careerInformation;
    }

    public void setCareerInformation(String careerInformation) {
        this.careerInformation = careerInformation;
    }
}
