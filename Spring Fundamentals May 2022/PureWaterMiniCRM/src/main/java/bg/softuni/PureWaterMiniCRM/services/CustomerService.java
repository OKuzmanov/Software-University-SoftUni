package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;

import java.util.List;

public interface CustomerService {
    CustomerServiceModel addCustomer(CustomerServiceModel customerServiceModel);

    CustomerServiceModel findCustomerByCompanyName(String companyName);

    List<CustomerServiceModel> getAllCustomers();
}