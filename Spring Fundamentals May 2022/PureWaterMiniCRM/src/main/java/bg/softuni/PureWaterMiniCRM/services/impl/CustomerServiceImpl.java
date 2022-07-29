package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
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
        List<Customer> allCustomers = this.customerRepo.findAll();
        return allCustomers
                .stream()
                .map(e -> this.modelMapper.map(e, CustomerServiceModel.class))
                .collect(Collectors.toList());
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
}
