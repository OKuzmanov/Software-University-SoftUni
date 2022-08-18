package bg.softuni.PureWaterMiniCRM.config;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import bg.softuni.PureWaterMiniCRM.services.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/users/register", "/users/login", "/about", "/maintenance").permitAll()
                .antMatchers("/pages/admins").hasRole(RoleEnum.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/orders", "/api/orders/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/customers", "/api/customers/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/suppliers", "/api/suppliers/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products", "/api/products/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/raw/materials", "/api/raw/materials/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/api/users", "/api/users/{id}", "/api/users/{id}/orders").permitAll()
                //Most Probably doesn't work due to Spring boot security CSRF restrictions
                .antMatchers(HttpMethod.POST,"/api/orders").permitAll()
                //Most Probably doesn't work due to Spring boot security CSRF restrictions
                .antMatchers(HttpMethod.DELETE,"/api/orders/{id}").permitAll()
                //Most Probably doesn't work due to Spring boot security CSRF restrictions
                .antMatchers(HttpMethod.PATCH,"/api/orders/{id}").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/home")
                .failureForwardUrl("/users/login-error")
        .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
