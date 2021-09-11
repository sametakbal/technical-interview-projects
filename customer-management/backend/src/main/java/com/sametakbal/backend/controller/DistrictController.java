package com.sametakbal.backend.controller;

import com.sametakbal.backend.entity.District;
import com.sametakbal.backend.service.IDistrictService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/districts")
@AllArgsConstructor
public class DistrictController {

    private final IDistrictService service;

    @GetMapping
    public ResponseEntity<List<District>> getDistricts(@RequestParam Integer cityId){
        List<District> districts;
        try {
            districts = service.districts(cityId);
        }catch (IllegalStateException ex){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }
}
