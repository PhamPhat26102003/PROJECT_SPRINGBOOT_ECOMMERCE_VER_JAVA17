package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.ShoppingCart;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
