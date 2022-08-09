package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository mockCustomerRepo;

    @Test
    public void testCustomerRepository_() {
        Customer savedEntity = mockCustomerRepo.save(new Customer("testName", "tm@gmail.com", "12345678910", "Test Address", "Test Desc", null));

        Customer resultEntity = mockCustomerRepo.findByCompanyName(savedEntity.getCompanyName()).get();

        Assertions.assertEquals(savedEntity.getCompanyName(), resultEntity.getCompanyName());
        Assertions.assertEquals(savedEntity.getEmail(), resultEntity.getEmail());
        Assertions.assertEquals(savedEntity.getDescription(), resultEntity.getDescription());
        Assertions.assertEquals(savedEntity.getAddress(), resultEntity.getAddress());
        Assertions.assertEquals(savedEntity.getPhoneNumber(), resultEntity.getPhoneNumber());
        Assertions.assertNull(resultEntity.getUser());
    }
}
