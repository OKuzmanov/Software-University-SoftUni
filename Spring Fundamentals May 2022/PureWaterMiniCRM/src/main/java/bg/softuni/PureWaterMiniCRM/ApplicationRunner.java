package bg.softuni.PureWaterMiniCRM;

import bg.softuni.PureWaterMiniCRM.models.entities.*;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final RawMaterialService rawMaterialService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private UserEntity u1;
    private UserEntity u2;
    private UserEntity u3;

    private Customer c1;
    private Customer c2;
    private Customer c3;

    @Autowired
    public ApplicationRunner(RoleService roleService, UserService userService, SupplierService supplierService, CustomerService customerService, RawMaterialService rawMaterialService, ProductService productService, OrderService orderService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.rawMaterialService = rawMaterialService;
        this.productService = productService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
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

            u1 = new UserEntity("oleg4o", "Oleg", "Kuzmanov", passwordEncoder.encode("12345"),"ok@c.com");
            u1.setRole(Set.of(user, admin));

            u2 = new UserEntity("benati", "Albena", "Yazovska", passwordEncoder.encode("12345"),"ay@c.com");
            u2.setRole(Set.of(user, moderator));

            u3 = new UserEntity("pesh", "Pesho", "Peshev", passwordEncoder.encode("12345"),"pp@c.com");
            u3.setRole(Set.of(user));

            this.userService.saveAll(List.of(u1, u2, u3));
        }

        if(supplierService.isRepoEmpty()) {
            Supplier s1 = new Supplier("Brothers Co.", "brothers.co@gmail.com", "0878456811", "London str.80", "The ultimate company for supply of raw materials.", this.getRandomUserEntity());
            Supplier s2 = new Supplier("Fast Supply Ltd.", "fs.ltd@gmail.com", "0972845720", "Mastricht str.22", "Company for super fast supply of raw materials.", this.getRandomUserEntity());
            Supplier s3 = new Supplier("Euro Supply ltd.", "eusro.supp@gmail.com", "0874562192", "Veliko Turnovo str.12", "Company for supply of raw materials in Europe.", this.getRandomUserEntity());
            Supplier s4 = new Supplier("Sea supply ltd.", "sea.supp@gmail.com", "0878296617", "Burgas str.51", "Company for supply of raw materials from overseas.", this.getRandomUserEntity());

            this.supplierService.saveAll(List.of(s1, s2, s3, s4));
        }

        if(customerService.isRepoEmpty()) {
            c1 = new Customer("Ganchev and Brothers ltd.", "gandb@gmail.com", "0878632074", "Sofia str.Vasil Levski", "A leading distributor of water and other non-alcoholic beverages in Sofia region.", this.getRandomUserEntity());
            c2 = new Customer("Tsare and Co.", "tsare@gmail.com", "0878456627", "Veliko Turnovo str.15", "A leading distributor of water and other non-alcoholic beverages in Veliko Turnovo region.", this.getRandomUserEntity());
            c3 = new Customer("Seagull Distributors ltd.", "seagull.distribute@gmail.com", "0878421275", "Varna str.Morska Gradina", "A leading distributor of water and other non-alcoholic beverages in Varna region.", this.getRandomUserEntity());

            this.customerService.saveAll(List.of(c1, c2, c3));
        }

        if(rawMaterialService.isRepoEmpty()) {
            RawMaterial r1 = new RawMaterial(0, RawMaterialType.BOTTLE_HALF_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r2 = new RawMaterial(0, RawMaterialType.BOTTLE_LITRE_AND_HALF, this.supplierService.getRandomSupplier());
            RawMaterial r3 = new RawMaterial(0, RawMaterialType.BOTTLE_TEN_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r4 = new RawMaterial(0, RawMaterialType.BOTTLE_NINETEEN_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r5 = new RawMaterial(0, RawMaterialType.BOTTLENECK, this.supplierService.getRandomSupplier());
            RawMaterial r6 = new RawMaterial(0, RawMaterialType.CAP_HALF_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r7 = new RawMaterial(0, RawMaterialType.CAP_LITRE_AND_HALF, this.supplierService.getRandomSupplier());
            RawMaterial r8 = new RawMaterial(0, RawMaterialType.CAP_NINETEEN_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r9 = new RawMaterial(0, RawMaterialType.CAP_TEN_LITRE, this.supplierService.getRandomSupplier());
            RawMaterial r10 = new RawMaterial(0, RawMaterialType.GLUE, this.supplierService.getRandomSupplier());
            RawMaterial r11 = new RawMaterial(0, RawMaterialType.HANDLE_TEN_LITRES, this.supplierService.getRandomSupplier());
            RawMaterial r12 = new RawMaterial(0, RawMaterialType.LABEL, this.supplierService.getRandomSupplier());

            this.rawMaterialService.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12));
        }

        if(productService.isRepoEmpty()) {
            Product p1 = new Product(0, ProductCategoryEnum.HALF_LITRE);
            Product p2 = new Product(0, ProductCategoryEnum.LITRE_AND_HALF);
            Product p3 = new Product(0, ProductCategoryEnum.TEN_LITRES);
            Product p4 = new Product(0, ProductCategoryEnum.NINETEEN_LITRES);

            this.productService.saveAll(List.of(p1, p2, p3, p4));
        }

        if (orderService.isRepoEmpty()) {
            Order o1 = new Order("Litre and a Half", 16, ProductCategoryEnum.LITRE_AND_HALF, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o1.setTotalPrice(ProductCategoryEnum.LITRE_AND_HALF.getPrice().multiply(new BigDecimal(16)));
            Order o2 = new Order("Half Litre", 39, ProductCategoryEnum.HALF_LITRE, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o2.setTotalPrice(ProductCategoryEnum.HALF_LITRE.getPrice().multiply(new BigDecimal(39)));
            Order o3 = new Order("Nineteen Litres", 115, ProductCategoryEnum.NINETEEN_LITRES, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o3.setTotalPrice(ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(new BigDecimal(115)));
            Order o4 = new Order("Ten Litres", 10, ProductCategoryEnum.TEN_LITRES, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o4.setTotalPrice(ProductCategoryEnum.TEN_LITRES.getPrice().multiply(new BigDecimal(10)));

            this.orderService.saveAll(List.of(o1, o2, o3, o4));
        }
    }

    private Customer getRandomCustomerEntity() {
        List<Customer> customers = List.of(c1, c2, c3);
        return customers.get(new Random().nextInt(0, 3));
    }

    private UserEntity getRandomUserEntity() {
        List<UserEntity> users = List.of(u1, u2, u3);
        return users.get(new Random().nextInt(0, 3));
    }
}
