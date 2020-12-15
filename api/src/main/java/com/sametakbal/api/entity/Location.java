package com.sametakbal.api.entity;

public class Location {
    private int id;
    private String location;

    public Location(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public Location() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
