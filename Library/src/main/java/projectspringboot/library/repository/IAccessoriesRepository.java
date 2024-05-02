package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Accessories;

import java.util.List;

@Repository
public interface IAccessoriesRepository extends JpaRepository<Accessories, Long> {

    @Query("SELECT a FROM Accessories a WHERE CONCAT(a.name, a.category) LIKE %?1%")
    List<Accessories> findAll(String keyword);
    @Query("SELECT a FROM Accessories a WHERE a.is_activated=true AND a.is_deleted=false")
    List<Accessories> findAll();
    @Query("SELECT a FROM Accessories a WHERE a.name LIKE %?1%")
    List<Accessories> searchAccessories(String keyword);


    //San pham lien quan
    @Query("SELECT a FROM Accessories a INNER JOIN Category c ON c.id = a.category.id WHERE a.category.id = ?1")
    List<Accessories> relatedProduct(Long categoryId);
}
