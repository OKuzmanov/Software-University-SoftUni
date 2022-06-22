package bg.softuni.examprepshoppinglist.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name="needed_before")
    private LocalDateTime neededBefore;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    public Product() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
