package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        return userRepository
//                .findByUsername(username)
//                .map(this::map)
//                .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found!"));
//    }

//    private UserDetails map(UserEntity userEntity) {
//        return User
//                .withUsername(userEntity.getUsername())
//                .password(userEntity.getPassword())
//                .authorities(userEntity.getRole()
//                        .stream()
//                        .map(this::map)
//                        .collect(Collectors.toList()))
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found!"));
    }

    private UserDetails map(UserEntity userEntity) {

        PureWaterUserDetails userDetails = new PureWaterUserDetails(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getRole()
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toList()));

        userDetails.setDeleted(userEntity.getIsDeleted());

        return userDetails;
    }

    private GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}
