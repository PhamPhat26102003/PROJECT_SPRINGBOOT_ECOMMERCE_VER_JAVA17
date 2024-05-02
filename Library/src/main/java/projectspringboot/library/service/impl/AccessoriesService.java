package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.Accessories;
import projectspringboot.library.repository.IAccessoriesRepository;
import projectspringboot.library.service.IAccessoriesService;
import projectspringboot.library.service.IStoreService;

import java.util.List;

@Service
public class AccessoriesService implements IAccessoriesService {
    @Autowired
    private IAccessoriesRepository accessoriesRepository;
    @Autowired
    private IStoreService storeService;

    @Override
    public List<Accessories> findAll(String keyword) {
        if(keyword != null){
            return accessoriesRepository.findAll(keyword);
        }
        return accessoriesRepository.findAll();
    }


    @Override
    public Accessories findById(Long id) {
        return accessoriesRepository.getById(id);
    }

    @Override
    public Accessories save(Accessories accessories) {
        return accessoriesRepository.save(accessories);
    }

    @Override
    public Accessories updateAccessories(Accessories accessories) {
        try{
            Accessories accessoriesUpdate = accessoriesRepository.getById(accessories.getId());
            accessoriesUpdate.setName(accessories.getName());
            accessoriesUpdate.setSpecifications(accessories.getSpecifications());
            accessoriesUpdate.setCurrentQuantity(accessories.getCurrentQuantity());
            accessoriesUpdate.setCostPrice(accessories.getCostPrice());
            accessoriesUpdate.setCategory(accessories.getCategory());
            if(!accessories.getImage().isEmpty()){
                storeService.deleteFile(accessories.getFilename());
                String filename = storeService.storeFile(accessories.getImage());
                accessoriesUpdate.setFilename(filename);
            }
            return accessoriesRepository.save(accessoriesUpdate);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Accessories> searchAccessories(String keyword) {
        List<Accessories> result = accessoriesRepository.searchAccessories(keyword);
        return result;
    }

    @Override
    public List<Accessories> relatedProduct(Long categoryId) {
        return accessoriesRepository.relatedProduct(categoryId);
    }

    @Override
    public void activatedById(Long id) {
        Accessories accessories = accessoriesRepository.getById(id);
        accessories.set_activated(true);
        accessories.set_deleted(false);
        accessoriesRepository.save(accessories);
    }

    @Override
    public void deletedById(Long id) {
        Accessories accessories = accessoriesRepository.getById(id);
        accessories.set_deleted(true);
        accessories.set_activated(false);
        accessoriesRepository.save(accessories);
    }
}
