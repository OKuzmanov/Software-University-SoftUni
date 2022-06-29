package bg.softuni.PureWaterMiniCRM;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationRunner(RoleService roleService, UserService userService, SupplierService supplierService, CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.dbInit();
    }

    private void dbInit() {
        if(this.roleService.isRepoEmpty()) {
            Role r1 = new Role(RoleEnum.ADMIN);
            Role r2 = new Role(RoleEnum.MODERATOR);
            Role r3 = new Role(RoleEnum.USER);

            this.roleService.saveAll(List.of(r1, r2, r3));
        }

        if(userService.isRepoEmpty()) {
            Role admin = this.roleService.findByName(RoleEnum.ADMIN).get();
            Role moderator = this.roleService.findByName(RoleEnum.MODERATOR).get();
            Role user = this.roleService.findByName(RoleEnum.USER).get();

            User u1 = new User("oleg4o", "Oleg", "Kuzmanov", passwordEncoder.encode("12345"),"ok@c.com");
            u1.setRole(Set.of(user, admin));

            User u2 = new User("bentai", "Albena", "Yazovska", passwordEncoder.encode("12345"),"ay@c.com");
            u2.setRole(Set.of(user, moderator));

            User u3 = new User("pesh", "Pesho", "Peshev", passwordEncoder.encode("12345"),"pp@c.com");
            u3.setRole(Set.of(user));

            this.userService.saveAll(List.of(u1, u2, u3));
        }

        if(supplierService.isRepoEmpty()) {
            Supplier s1 = new Supplier("Brothers Co.", "brothers.co@gmail.com", "0878456811", "London str.80", "The ultimate company for supply of raw materials.");
            Supplier s2 = new Supplier("Fast Supply Ltd.", "fs.ltd@gmail.com", "0972845720", "Mastricht str.22", "Company for super fast supply of raw materials.");
            Supplier s3 = new Supplier("Euro Supply ltd.", "eusro.supp@gmail.com", "0874562192", "Veliko Turnovo str.12", "Company for supply of raw materials in Europe.");
            Supplier s4 = new Supplier("Sea supply ltd.", "sea.supp@gmail.com", "0878296617", "Burgas str.51", "Company for supply of raw materials from overseas.");

            this.supplierService.saveAll(List.of(s1, s2, s3, s4));
        }

        if(customerService.isRepoEmpty()) {
            Customer c1 = new Customer("Ganchev and Brothers ltd.", "gandb@gmail.com", "0878632074", "Sofia str.Vasil Levski", "A leading distributor of water and other non-alcoholic beverages in Sofia region.");
            Customer c2 = new Customer("Tsare and Co.", "tsare@gmail.com", "0878456627", "Veliko Turnovo str.15", "A leading distributor of water and other non-alcoholic beverages in Veliko Turnovo region.");
            Customer c3 = new Customer("Seagull Distributors ltd.", "seagull.distribute@gmail.com", "0878421275", "Varna str.Morska Gradina", "A leading distributor of water and other non-alcoholic beverages in Varna region.");

            this.customerService.saveAll(List.of(c1, c2, c3));
        }
    }
}
