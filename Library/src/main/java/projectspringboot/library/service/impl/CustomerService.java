package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.dto.CustomerDto;
import projectspringboot.library.model.Customer;
import projectspringboot.library.repository.ICustomerRepository;
import projectspringboot.library.repository.IRoleRepository;
import projectspringboot.library.service.ICustomerService;

import java.util.Arrays;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setAddress(customerDto.getAddress());
        customer.setCountry(customerDto.getCountry());
        customer.setCity(customerDto.getCity());
        customer.setPhone(customerDto.getPhone());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerUpdate = customerRepository.findByUsername(customer.getUsername());
        customerUpdate.setAddress(customer.getAddress());
        customerUpdate.setCountry(customer.getCountry());
        customerUpdate.setCity(customer.getCity());
        customerUpdate.setPhone(customer.getPhone());
        return customerRepository.save(customerUpdate);
    }
}
