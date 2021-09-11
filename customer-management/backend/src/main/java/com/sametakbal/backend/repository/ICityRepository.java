package com.sametakbal.backend.repository;

import com.sametakbal.backend.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface ICityRepository extends CrudRepository<City,Integer> {
}
