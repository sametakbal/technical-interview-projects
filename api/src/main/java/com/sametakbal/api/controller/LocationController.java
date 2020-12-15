package com.sametakbal.api.controller;

import com.sametakbal.api.entity.Location;
import com.sametakbal.api.entity.User;
import com.sametakbal.api.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/locations"
)
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Location> fetchLocations(@RequestParam(name = "term")String term){
        return locationService.getLocations(term);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean registerLocation(@RequestBody Location location){
        return locationService.addLocation(location);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean updateLocation(@RequestBody Location location){
        return locationService.updateLocation(location);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}"
    )
    public boolean removeLocation(@PathVariable("id") int id){
        return locationService.deleteLocation(id);
    }
}
