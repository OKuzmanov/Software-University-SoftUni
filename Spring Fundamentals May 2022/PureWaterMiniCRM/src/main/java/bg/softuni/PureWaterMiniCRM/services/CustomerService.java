package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.CustomerViewModel;

import java.util.List;

public interface CustomerService {
    CustomerServiceModel addCustomer(CustomerServiceModel customerServiceModel, PureWaterUserDetails userDetails);

    CustomerServiceModel findCustomerByCompanyName(String companyName);

    List<CustomerServiceModel> getAllCustomers();

    boolean isRepoEmpty();

    void saveAll(List<Customer> customers);

    List<CustomerViewModel> fetchAll();

    CustomerViewModel fetchById(Long id);

    Customer findById(int id);

    List<Customer> findAll();

    CustomerServiceModel updateCustomer(Long id, CustomerServiceModel customerServiceModel);

    boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long customerId);

    boolean deleteCustomer(long id);
}
