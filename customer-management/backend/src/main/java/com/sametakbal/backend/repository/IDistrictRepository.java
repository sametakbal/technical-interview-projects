package com.sametakbal.backend.repository;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.entity.District;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDistrictRepository extends CrudRepository<District,Integer> {

    List<District> findAllByCityOrderByDistrict(City city);
}
