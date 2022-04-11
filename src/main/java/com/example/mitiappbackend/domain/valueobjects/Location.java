package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class Location {

    private String location;

    public Location(String location) {
        this.location = notBlank(location);
    }

    protected Location() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
