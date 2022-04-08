package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class Location {

    private String location;

    public Location(String location) {
        this.location = notBlank(location);
    }

    protected Location() {
    }

    @Override
    public String toString() {
        return "Location{" +
                "location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
