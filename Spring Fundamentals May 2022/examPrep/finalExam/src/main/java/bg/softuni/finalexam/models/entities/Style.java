package bg.softuni.finalexam.models.entities;

import bg.softuni.finalexam.models.enums.StyleEnum;

import javax.persistence.*;

@Entity
@Table(name = "styles")
public class Style extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, name = "style_name")
    private StyleEnum styleName;

    @Column(columnDefinition = "text")
    private String description;

    public Style() {
    }

    public Style(StyleEnum styleName, String description) {
        this.styleName = styleName;
        this.description = description;
    }

    public StyleEnum getStyleName() {
        return styleName;
    }

    public void setStyleName(StyleEnum styleName) {
        this.styleName = styleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
