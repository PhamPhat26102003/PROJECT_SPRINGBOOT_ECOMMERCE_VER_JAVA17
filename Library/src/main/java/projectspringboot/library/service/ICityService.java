package projectspringboot.library.service;

import projectspringboot.library.model.City;

import java.util.List;

public interface ICityService {
    List<City> findAll();
}
