package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class FirstName {

    private String firstName;

    public FirstName(String firstName) {
        this.firstName = notBlank(firstName);
    }

    protected FirstName() {}

    @Override
    public String toString() {
        return "FirstName{" +
                "firstName='" + firstName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName1 = (FirstName) o;
        return Objects.equals(firstName, firstName1.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }
}
