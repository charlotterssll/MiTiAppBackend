package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class LastName {

    private String lastName;

    public LastName(String lastName) {
        this.lastName = notBlank(lastName);
    }

    protected LastName() {
    }

    @Override
    public String toString() {
        return "LastName{" +
                "lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName1 = (LastName) o;
        return Objects.equals(lastName, lastName1.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName);
    }
}
