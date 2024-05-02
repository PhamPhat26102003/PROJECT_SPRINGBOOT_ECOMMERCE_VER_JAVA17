package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
}
