package com.example.mitiappbackend.domain.entities;

import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACEID")
    private Long placeID;

    @Embedded
    private Locality locality;

    @Embedded
    private Location location;

    public Place(Locality locality, Location location) {
        this.locality = notNull(locality);
        this.location = notNull(location);
    }

    protected Place() {
    }

    public Long getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Long placeID) {
        this.placeID = notNull(placeID);
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = notNull(locality);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = notNull(location);
    }
}
