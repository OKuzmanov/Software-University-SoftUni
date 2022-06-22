package bg.softuni.examprepbattleships.models.binding;

import bg.softuni.examprepbattleships.models.entities.Category;
import bg.softuni.examprepbattleships.models.entities.User;
import bg.softuni.examprepbattleships.models.enums.CategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AddShipBindingModel {
    @NotEmpty
    @Size(min = 2, max = 10)
    private String name;

    @NotNull
    @Positive
    private Long health;

    @NotNull
    @Positive
    private Long power;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @NotNull
    private CategoryEnum category;

    public AddShipBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHealth() {
        return health;
    }

    public void setHealth(Long health) {
        this.health = health;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
