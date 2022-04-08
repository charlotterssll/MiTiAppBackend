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
    @AttributeOverride(name = "value", column = @Column(name = "LOCALITY"))
    private Locality locality;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "LOCATION"))
    private Location location;

    public Place(Locality locality, Location location) {
        this.locality = notNull(locality);
        this.location = notNull(location);
    }

    protected Place() {
    }

    public Locality getLocality() {
        return locality;
    }

    public Location getLocation() {
        return location;
    }

}
