package bg.softuni.examprepbattleships.models.serviceModels;

import bg.softuni.examprepbattleships.models.entities.User;
import bg.softuni.examprepbattleships.models.enums.CategoryEnum;

import java.time.LocalDate;

public class ShipServiceModel {

    private Long id;

    private String name;

    private Long health;

    private Long power;

    private LocalDate created;

    private User user;

    private CategoryEnum category;

    public ShipServiceModel() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
