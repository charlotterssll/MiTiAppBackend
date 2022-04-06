package com.example.mitiappbackend.domain;

import javax.persistence.*;

@Entity
@Table(name = "PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACEID")
    private Long placeID;

    @Column(name = "LOCALITY")
    private String locality;

    @Column(name = "LOCATION")
    private String location;

    public Place(String locality, String location) {
        this.locality = locality;
        this.location = location;
    }

    public Place() {
    }

    public Long getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Long placeID) {
        this.placeID = placeID;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
