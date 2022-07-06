package bg.softuni.PureWaterMiniCRM;

import bg.softuni.PureWaterMiniCRM.models.entities.*;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.modelmapper.ModelMapper;
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
    private final RawMaterialService rawMaterialService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationRunner(RoleService roleService, UserService userService, SupplierService supplierService, CustomerService customerService, RawMaterialService rawMaterialService, ProductService productService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.rawMaterialService = rawMaterialService;
        this.productService = productService;
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

            UserEntity u1 = new UserEntity("oleg4o", "Oleg", "Kuzmanov", passwordEncoder.encode("12345"),"ok@c.com");
            u1.setRole(Set.of(user, admin));

            UserEntity u2 = new UserEntity("benati", "Albena", "Yazovska", passwordEncoder.encode("12345"),"ay@c.com");
            u2.setRole(Set.of(user, moderator));

            UserEntity u3 = new UserEntity("pesh", "Pesho", "Peshev", passwordEncoder.encode("12345"),"pp@c.com");
            u3.setRole(Set.of(user));

            this.userService.saveAll(List.of(u1, u2, u3));
        }

        if(supplierService.isRepoEmpty()) {
            UserServiceModel usm = this.userService.findById(2);
            UserEntity userEntity = null;
            if(usm != null) {
                userEntity = this.modelMapper.map(usm, UserEntity.class);
            }

            Supplier s1 = new Supplier("Brothers Co.", "brothers.co@gmail.com", "0878456811", "London str.80", "The ultimate company for supply of raw materials.", userEntity);
            Supplier s2 = new Supplier("Fast Supply Ltd.", "fs.ltd@gmail.com", "0972845720", "Mastricht str.22", "Company for super fast supply of raw materials.", userEntity);
            Supplier s3 = new Supplier("Euro Supply ltd.", "eusro.supp@gmail.com", "0874562192", "Veliko Turnovo str.12", "Company for supply of raw materials in Europe.", userEntity);
            Supplier s4 = new Supplier("Sea supply ltd.", "sea.supp@gmail.com", "0878296617", "Burgas str.51", "Company for supply of raw materials from overseas.", userEntity);

            this.supplierService.saveAll(List.of(s1, s2, s3, s4));
        }

        if(customerService.isRepoEmpty()) {
            UserServiceModel usm = this.userService.findById(1);
            UserEntity userEntity = null;
            if(usm != null) {
                userEntity = this.modelMapper.map(usm, UserEntity.class);
            }
            Customer c1 = new Customer("Ganchev and Brothers ltd.", "gandb@gmail.com", "0878632074", "Sofia str.Vasil Levski", "A leading distributor of water and other non-alcoholic beverages in Sofia region.", userEntity);
            Customer c2 = new Customer("Tsare and Co.", "tsare@gmail.com", "0878456627", "Veliko Turnovo str.15", "A leading distributor of water and other non-alcoholic beverages in Veliko Turnovo region.", userEntity);
            Customer c3 = new Customer("Seagull Distributors ltd.", "seagull.distribute@gmail.com", "0878421275", "Varna str.Morska Gradina", "A leading distributor of water and other non-alcoholic beverages in Varna region.", userEntity);

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
    }
}
