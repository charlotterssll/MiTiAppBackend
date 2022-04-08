package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class Locality {

    private String locality;

    public Locality(String locality) {
        this.locality = notBlank(locality);
    }

    protected Locality() {
    }

    @Override
    public String toString() {
        return "Locality{" +
                "locality='" + locality + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locality locality1 = (Locality) o;
        return Objects.equals(locality, locality1.locality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locality);
    }
}
