package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.OrderDetail;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
