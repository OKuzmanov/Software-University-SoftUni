package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    boolean isRepoEmpty();

    void saveAll(List<Role> roles);

    Optional<Role> findByName(RoleEnum name);
}
