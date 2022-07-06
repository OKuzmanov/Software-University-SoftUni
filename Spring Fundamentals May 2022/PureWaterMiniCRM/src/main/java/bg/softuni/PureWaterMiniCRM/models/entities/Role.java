package bg.softuni.PureWaterMiniCRM.models.entities;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnum name;

    @ManyToMany(mappedBy = "role")
    private Set<UserEntity> userEntities;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return Collections.unmodifiableSet(userEntities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
