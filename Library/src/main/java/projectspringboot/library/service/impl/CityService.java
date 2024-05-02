package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspringboot.library.model.City;
import projectspringboot.library.repository.ICityRepository;
import projectspringboot.library.service.ICityService;

import java.util.List;

@Service
public class CityService implements ICityService {
    @Autowired
    private ICityRepository cityRepository;
    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
