package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.CartItem;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
}
