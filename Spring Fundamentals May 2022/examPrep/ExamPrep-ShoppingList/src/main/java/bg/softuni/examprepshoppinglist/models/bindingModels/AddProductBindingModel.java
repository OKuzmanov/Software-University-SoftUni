package bg.softuni.examprepshoppinglist.models.bindingModels;

import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AddProductBindingModel {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    @Size(min = 5)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime neededBefore;

    @NotNull
    private CategoryEnum category;

    public AddProductBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getNeededBefore() {
        return neededBefore;
    }

    public void setNeededBefore(LocalDateTime neededBefore) {
        this.neededBefore = neededBefore;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
