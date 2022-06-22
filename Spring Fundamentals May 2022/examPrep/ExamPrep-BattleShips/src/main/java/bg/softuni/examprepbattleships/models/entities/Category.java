package bg.softuni.examprepbattleships.models.entities;

import bg.softuni.examprepbattleships.models.enums.CategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CategoryEnum name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Category() {
    }

    public Category(CategoryEnum name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
