package projectspringboot.library.service;

import projectspringboot.library.dto.CustomerDto;
import projectspringboot.library.model.Customer;

public interface ICustomerService {
    Customer findByUsername(String username);
    Customer save(CustomerDto customerDto);
    Customer update(Customer customer);
}
