package bg.softuni.PureWaterMiniCRM;

import bg.softuni.PureWaterMiniCRM.models.entities.*;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
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
    private final OrderHistoryService orderHistoryService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private UserEntity u1;
    private UserEntity u2;
    private UserEntity u3;

    private Customer c1;
    private Customer c2;
    private Customer c3;

    private Random random = new Random();

    @Autowired
    public ApplicationRunner(RoleService roleService, UserService userService, SupplierService supplierService,
                             CustomerService customerService, RawMaterialService rawMaterialService, ProductService productService,
                             OrderService orderService, OrderHistoryService orderHistoryService, ModelMapper modelMapper,
                             PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.rawMaterialService = rawMaterialService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
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
            Role r3 = new Role(RoleEnum.USER);

            this.roleService.saveAll(List.of(r1, r3));
        }

        if(userService.isRepoEmpty()) {
            Role admin = this.roleService.findByName(RoleEnum.ADMIN).get();
            Role user = this.roleService.findByName(RoleEnum.USER).get();

            u1 = new UserEntity("oleg4o", "Oleg", "Kuzmanov", passwordEncoder.encode("12345"),"ok@c.com");
            u1.setRole(Set.of(user, admin));

            u2 = new UserEntity("benati", "Albena", "Yazovska", passwordEncoder.encode("12345"),"ay@c.com");
            u2.setRole(Set.of(user));

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
            RawMaterial r1 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.BOTTLE_HALF_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r2 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.BOTTLE_LITRE_AND_HALF, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r3 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.BOTTLE_TEN_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r4 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.BOTTLE_NINETEEN_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r5 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.BOTTLENECK, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r6 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.CAP_HALF_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r7 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.CAP_LITRE_AND_HALF, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r8 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.CAP_NINETEEN_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r9 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.CAP_TEN_LITRE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r10 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.GLUE, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r11 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.HANDLE_TEN_LITRES, this.supplierService.getRandomSupplier(), LocalDateTime.now());
            RawMaterial r12 = new RawMaterial(random.nextInt(1, 75), RawMaterialType.LABEL, this.supplierService.getRandomSupplier(), LocalDateTime.now());

            this.rawMaterialService.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12));
        }

        if(productService.isRepoEmpty()) {
            Product p1 = new Product(random.nextInt(1, 150), ProductCategoryEnum.HALF_LITRE, LocalDateTime.now());
            Product p2 = new Product(random.nextInt(1, 150), ProductCategoryEnum.LITRE_AND_HALF, LocalDateTime.now());
            Product p3 = new Product(random.nextInt(1, 150), ProductCategoryEnum.TEN_LITRES, LocalDateTime.now());
            Product p4 = new Product(random.nextInt(1, 150), ProductCategoryEnum.NINETEEN_LITRES, LocalDateTime.now());

            this.productService.saveAll(List.of(p1, p2, p3, p4));
        }

        if (orderService.isRepoEmpty()) {
            Order o1 = new Order("Litre and a Half", random.nextInt(1, 16), ProductCategoryEnum.LITRE_AND_HALF, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o1.setTotalPrice(ProductCategoryEnum.LITRE_AND_HALF.getPrice().multiply(new BigDecimal(o1.getQuantity())));
            Order o2 = new Order("Half Litre", random.nextInt(1, 39), ProductCategoryEnum.HALF_LITRE, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o2.setTotalPrice(ProductCategoryEnum.HALF_LITRE.getPrice().multiply(new BigDecimal(o2.getQuantity())));
            Order o3 = new Order("Nineteen Litres", random.nextInt(1, 115), ProductCategoryEnum.NINETEEN_LITRES, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o3.setTotalPrice(ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(new BigDecimal(o3.getQuantity())));
            Order o4 = new Order("Ten Litres", random.nextInt(1, 10), ProductCategoryEnum.TEN_LITRES, "Test description", LocalDateTime.of(2022, Month.AUGUST, 30, 23, 59), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o4.setTotalPrice(ProductCategoryEnum.TEN_LITRES.getPrice().multiply(new BigDecimal(o4.getQuantity())));

            this.orderService.saveAll(List.of(o1, o2, o3, o4));
        }

        if (orderHistoryService.isRepoEmpty()) {
            OrderHistory o1 = new OrderHistory("Litre and a Half", random.nextInt(1, 11), ProductCategoryEnum.LITRE_AND_HALF, "Test description", LocalDateTime.now(), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o1.setTotalPrice(ProductCategoryEnum.LITRE_AND_HALF.getPrice().multiply(BigDecimal.valueOf(o1.getQuantity())));
            OrderHistory o2 = new OrderHistory("Half Litre", random.nextInt(1, 102), ProductCategoryEnum.HALF_LITRE, "Test description", LocalDateTime.now(), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o2.setTotalPrice(ProductCategoryEnum.HALF_LITRE.getPrice().multiply(new BigDecimal(o2.getQuantity())));
            OrderHistory o3 = new OrderHistory("Nineteen Litres", random.nextInt(1, 75), ProductCategoryEnum.NINETEEN_LITRES, "Test description", LocalDateTime.now(), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o3.setTotalPrice(ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(new BigDecimal(o3.getQuantity())));
            OrderHistory o4 = new OrderHistory("Ten Litres", random.nextInt(1, 10), ProductCategoryEnum.TEN_LITRES, "Test description", LocalDateTime.now(), this.getRandomUserEntity(), this.getRandomCustomerEntity());
            o4.setTotalPrice(ProductCategoryEnum.TEN_LITRES.getPrice().multiply(new BigDecimal(o4.getQuantity())));

            this.orderHistoryService.saveAll(List.of(o1, o2, o3, o4));
        }
    }

    private Customer getRandomCustomerEntity() {
        List<Customer> allCustomers = this.customerService.findAll();
        return allCustomers.get(new Random().nextInt(0, allCustomers.size()));
    }

    private UserEntity getRandomUserEntity() {
        List<UserEntity> allUsers = this.userService.findAll();
        return allUsers.get(new Random().nextInt(0, allUsers.size()));
    }
}
