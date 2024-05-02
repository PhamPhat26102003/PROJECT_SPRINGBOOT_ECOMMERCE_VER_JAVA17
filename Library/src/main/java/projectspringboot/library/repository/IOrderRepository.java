package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
}
