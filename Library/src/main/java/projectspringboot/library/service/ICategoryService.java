package projectspringboot.library.service;

import projectspringboot.library.dto.CategoryDto;
import projectspringboot.library.model.Category;

import java.util.List;

public interface ICategoryService {
    //Admin
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);
    List<Category> findAllByActivated();

    //Customer
//    List<CategoryDto> getProductFollowCategory();
}
