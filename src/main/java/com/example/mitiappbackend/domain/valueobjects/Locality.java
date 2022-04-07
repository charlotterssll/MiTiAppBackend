package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "LOCALITY"))
public class Locality {

    private String locality;

    public Locality(String locality) {
        this.locality = locality;
    }

    protected Locality() {
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

    @Override
    public String toString() {
        return "Locality{" +
                "locality='" + locality + '\'' +
                '}';
    }
}
