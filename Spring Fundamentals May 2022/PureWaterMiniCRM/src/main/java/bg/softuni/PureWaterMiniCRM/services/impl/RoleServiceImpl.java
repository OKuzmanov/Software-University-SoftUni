package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.repositories.RoleRepository;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    public final RoleRepository roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public boolean isRepoEmpty() {
        return this.roleRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Role> roles) {
        this.roleRepo.saveAll(roles);
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return this.roleRepo.findByName(name);
    }
}
