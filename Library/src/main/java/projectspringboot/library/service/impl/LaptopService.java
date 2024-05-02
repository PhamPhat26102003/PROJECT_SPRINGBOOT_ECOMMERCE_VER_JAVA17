package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.Laptop;
import projectspringboot.library.repository.ILaptopRepository;
import projectspringboot.library.service.ILaptopService;
import projectspringboot.library.service.IStoreService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaptopService implements ILaptopService {

    @Autowired
    private ILaptopRepository laptopRepository;
    @Autowired
    private IStoreService storeService;

    //Admin
    @Override
    public List<Laptop> findAll(String keyword) {
        if(keyword != null){
            return laptopRepository.findAll(keyword);
        }
        return laptopRepository.findAll();
    }

    @Override
    public Laptop save(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop updateLaptop(Laptop laptop) {
        try{
            Laptop laptopUpdate = laptopRepository.getById(laptop.getId());
            laptopUpdate.setName(laptop.getName());
            laptopUpdate.setDrive(laptop.getDrive());
            laptopUpdate.setScreen(laptop.getScreen());
            laptopUpdate.setCard(laptop.getCard());
            laptopUpdate.setOperatingSystem(laptop.getOperatingSystem());
            laptopUpdate.setCostPrice(laptop.getCostPrice());
            laptopUpdate.setCurrentQuantity(laptop.getCurrentQuantity());
            laptopUpdate.setCategory(laptop.getCategory());

            if(!laptop.getImage().isEmpty()){
                storeService.deleteFile(laptopUpdate.getFilename());
                String filename = storeService.storeFile(laptop.getImage());
                laptopUpdate.setFilename(filename);
            }
            return laptopRepository.save(laptopUpdate);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Laptop findById(Long id) {
        return laptopRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        Laptop laptop = laptopRepository.getById(id);
        laptop.set_deleted(true);
        laptop.set_activated(false);
        laptopRepository.save(laptop);
    }

    @Override
    public void enableById(Long id) {
        Laptop laptop = laptopRepository.getById(id);
        laptop.set_activated(true);
        laptop.set_deleted(false);
        laptopRepository.save(laptop);
    }

    @Override
    public Page<Laptop> pageLaptops(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        Page<Laptop> laptops = laptopRepository.pageLaptop(pageable);
        return laptops;
    }

    @Override
    public List<Laptop> relateProduct(Long categoryId) {
        return laptopRepository.getRelateProduct(categoryId);
    }

    private Page toPage(List<Laptop> list, Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public Page<Laptop> filterLaptop(String keyword, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Laptop> laptopList = laptopRepository.searchLaptopList(keyword);
        Page<Laptop> filter = toPage(laptopList, pageable);
        return filter;
    }

    //Customer
    @Override
    public Page<Laptop> getAllLaptop(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 16);
        Page<Laptop> laptops = laptopRepository.getAllLaptop(pageable);
        return laptops;
    }
}
