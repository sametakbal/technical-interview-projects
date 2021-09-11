package com.sametakbal.backend.service;

import com.sametakbal.backend.entity.District;

import java.util.List;

public interface IDistrictService extends IBaseService<District>{
    List<District> districts(Integer cityId);
}
