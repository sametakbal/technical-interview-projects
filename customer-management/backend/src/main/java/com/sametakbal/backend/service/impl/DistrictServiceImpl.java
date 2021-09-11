package com.sametakbal.backend.service.impl;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.entity.District;
import com.sametakbal.backend.repository.ICityRepository;
import com.sametakbal.backend.repository.IDistrictRepository;
import com.sametakbal.backend.service.IDistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements IDistrictService {
    private final IDistrictRepository repo;
    private final ICityRepository cityRepo;
    @Override
    public List<District> districts(Integer cityId) {
        if (cityId == null) {
            throw new IllegalStateException("City id cannot be null");
        }
        Optional<City> city = cityRepo.findById(cityId);
        if (city.isPresent()){
            return repo.findAllByCityOrderByDistrict(city.get());
        }
        throw new IllegalStateException("City not found");
    }


    @Override
    public List<District> all() {
        return null;
    }

    @Override
    public District entityWithId(Integer id) {
        return null;
    }

    @Override
    public void createOrUpdate(District entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public String isValid(District entity) {
        return null;
    }


}
