package bg.softuni.examprepshoppinglist.models.entities;

import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private CategoryEnum name;

    private String description;

    public Category(CategoryEnum name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category() {
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
