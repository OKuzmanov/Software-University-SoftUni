package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.CustomerViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.CustomerRepository;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepo, ModelMapper modelMapper, UserService userService) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public CustomerServiceModel addCustomer(CustomerServiceModel customerServiceModel, PureWaterUserDetails userDetails) {

        Customer customerToSave = this.modelMapper.map(customerServiceModel, Customer.class);

        customerToSave.setUser(this.modelMapper.map(this.userService.findUserByUsername(userDetails.getUsername()), UserEntity.class));

        this.customerRepo.save(customerToSave);

        CustomerServiceModel csm = this.modelMapper.map(customerToSave, CustomerServiceModel.class);
        csm.setUser(this.modelMapper.map(customerToSave.getUser(), UserServiceModel.class));

        return csm;
    }

    @Override
    public CustomerServiceModel findCustomerByCompanyName(String companyName) {

        Optional<Customer> customerOpt = this.customerRepo.findByCompanyName(companyName);

        return customerOpt.isEmpty()
                ? null
                : this.modelMapper.map(customerOpt.get(), CustomerServiceModel.class);
    }

    @Override
    public List<CustomerServiceModel> getAllCustomers() {
        return this.customerRepo.findAll()
                .stream()
                .filter(c -> !c.isDeleted())
                .map(e -> this.modelMapper.map(e, CustomerServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> all = this.customerRepo
                .findAll();
        return all
                .stream()
                .filter(c -> !c.isDeleted())
                .toList();
    }


    @Override
    public boolean isRepoEmpty() {
        return this.customerRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Customer> customers) {
        this.customerRepo.saveAll(customers);
    }

    @Override
    public List<CustomerViewModel> fetchAll() {
        List<Customer> all = this.customerRepo.findAll();
        return all
                .stream()
                .filter(c -> !c.isDeleted())
                .map(c -> this.modelMapper.map(c, CustomerViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerViewModel fetchById(Long id) {
        Optional<Customer> customerOpt = this.customerRepo.findById(id);

        CustomerViewModel cvm = customerOpt.map( c ->this.modelMapper.map(c, CustomerViewModel.class))
                .orElseThrow(() -> new ApiObjectNotFoundException(id, "Customer"));

        return cvm;
    }

    @Override
    public Customer findById(int id) {
        return customerRepo.findById(Long.valueOf(id))
                .orElseThrow(() -> new ObjectNotFoundException(Long.valueOf(id), "Customer"));
    }

    @Override
    public CustomerServiceModel updateCustomer(Long id, CustomerServiceModel customerServiceModel) {
        Optional<Customer> optCustomer = this.customerRepo.findById(id);

        if (optCustomer.isEmpty()) {
            throw new ObjectNotFoundException(id, "Customer");
        }

        Customer customerEntity = optCustomer.get();

        customerEntity.setCompanyName(customerServiceModel.getCompanyName());
        customerEntity.setEmail(customerServiceModel.getEmail());
        customerEntity.setPhoneNumber(customerServiceModel.getPhoneNumber());
        customerEntity.setAddress(customerServiceModel.getAddress());
        customerEntity.setDescription(customerServiceModel.getDescription());

        Customer savedEntity = this.customerRepo.save(customerEntity);

        return this.modelMapper.map(savedEntity, CustomerServiceModel.class);
    }

    @Override
    public boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long customerId) {
        Long userEntityId = this.customerRepo
                .findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, "Customer"))
                .getUser()
                .getId();

        if(userDetails.getId() == userEntityId) {
            return true;
        }

        boolean isAdmin = userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + RoleEnum.ADMIN));

        return isAdmin;
    }

    @Override
    public boolean deleteCustomer(long id) {
        Customer customerEntity = this.customerRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Customer"));

        customerEntity.setDeleted(true);

        this.customerRepo.save(customerEntity);

        return true;
    }
}
