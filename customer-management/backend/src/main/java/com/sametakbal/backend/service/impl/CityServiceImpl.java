package com.sametakbal.backend.service.impl;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.repository.ICityRepository;
import com.sametakbal.backend.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements ICityService {
    private final ICityRepository repo;
    @Override
    public List<City> all() {
        return (List<City>) repo.findAll();
    }

    @Override
    public City entityWithId(Integer id) {
        return null;
    }

    @Override
    public void createOrUpdate(City entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public String isValid(City entity) {
        return null;
    }
}
