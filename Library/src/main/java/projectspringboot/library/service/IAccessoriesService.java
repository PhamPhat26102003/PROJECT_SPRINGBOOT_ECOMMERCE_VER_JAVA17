package projectspringboot.library.service;

import org.apache.catalina.LifecycleState;
import projectspringboot.library.model.Accessories;

import java.util.List;

public interface IAccessoriesService {
    List<Accessories> findAll(String keyword);
    Accessories findById(Long id);
    Accessories save(Accessories accessories);
    Accessories updateAccessories(Accessories accessories);
    List<Accessories> searchAccessories(String keyword);
    List<Accessories> relatedProduct(Long categoryId);
    void activatedById(Long id);
    void deletedById(Long id);
}
