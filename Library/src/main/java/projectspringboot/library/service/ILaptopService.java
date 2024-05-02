package projectspringboot.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projectspringboot.library.model.Laptop;

import java.util.List;

public interface ILaptopService {

    //Admin
    List<Laptop> findAll(String keyword);
    Laptop save(Laptop laptop);
    Laptop updateLaptop(Laptop laptop);
    Laptop findById(Long id);
    void deleteById(Long id);
    void enableById(Long id);

    Page<Laptop> pageLaptops(int pageNo);

    List<Laptop> relateProduct(Long categoryId);

    Page<Laptop> filterLaptop(String keyword, int pageNo);

    //Customer
    Page<Laptop> getAllLaptop(int pageNo);
}
