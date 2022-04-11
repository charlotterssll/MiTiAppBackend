package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class Locality {

    private String locality;

    public Locality(String locality) {
        this.locality = notBlank(locality);
    }

    protected Locality() {
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
