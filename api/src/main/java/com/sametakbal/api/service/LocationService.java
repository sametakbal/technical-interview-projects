package com.sametakbal.api.service;

import com.sametakbal.api.dao.LocationDao;
import com.sametakbal.api.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService{
    private final LocationDao locationDao;

    @Autowired
    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public List<Location> getLocations(String term){
        return locationDao.get(term);
    }

    public boolean addLocation(Location location){
        return locationDao.add(location)==1;
    }

    public boolean updateLocation(Location location){
        return locationDao.update(location)==1;
    }
    public boolean deleteLocation(int id){
        return locationDao.remove(id)==1;
    }

}
