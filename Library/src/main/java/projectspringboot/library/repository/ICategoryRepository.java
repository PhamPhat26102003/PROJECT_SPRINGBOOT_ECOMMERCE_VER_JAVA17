package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectspringboot.library.dto.CategoryDto;
import projectspringboot.library.model.Category;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.is_activated = true and c.is_delete = false")
    List<Category> findAllByActivated();
//
//    @Query("select new projectspringboot.library.dto.CategoryDto(c.id, c.name, count(p.category.id))" +
//            " from Category c inner join Laptop p on p.category.id = c.id" +
//            " where c.is_activated=true and c.is_delete=false group by c.id")
//    List<CategoryDto> getProductFollowCategory();
}
