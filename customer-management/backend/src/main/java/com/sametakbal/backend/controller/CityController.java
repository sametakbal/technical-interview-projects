package com.sametakbal.backend.controller;

import com.sametakbal.backend.entity.City;
import com.sametakbal.backend.service.ICityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/cities")
@AllArgsConstructor
public class CityController {
    private final ICityService service;

    @GetMapping
    public ResponseEntity<List<City>> getCities(){
        return new ResponseEntity<>(service.all(),HttpStatus.OK);
    }
}
