package projectspringboot.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Laptop;

import java.util.List;

@Repository
public interface ILaptopRepository extends JpaRepository<Laptop, Long> {
    //Phan trang
    @Query("SELECT p FROM Laptop p")
    Page<Laptop> pageLaptop(Pageable pageable);

    @Query("SELECT p FROM Laptop p WHERE CONCAT(p.name, p.category) LIKE %?1%")
    List<Laptop> findAll(String keyword);

    @Query("SELECT p FROM Laptop p WHERE p.is_activated=true AND p.is_deleted=false")
    List<Laptop> findAll();

    //Tim kiem laptop
    @Query("SELECT p FROM Laptop p WHERE p.name LIKE %?1%")
    Page<Laptop> searchLaptop(String keyword, Pageable pageable);

    @Query("SELECT p FROM Laptop p WHERE p.name LIKE %?1%")
    List<Laptop> searchLaptopList(String keyword);

    //Customer
    //San pham lien quan
    @Query("SELECT p FROM Laptop p INNER JOIN Category c on c.id = p.category.id WHERE p.category.id = ?1")
    List<Laptop> getRelateProduct(Long categoryId);
    @Query("SELECT p FROM Laptop p WHERE p.is_activated=true AND p.is_deleted=false")
    Page<Laptop> getAllLaptop(Pageable pageable);
}
