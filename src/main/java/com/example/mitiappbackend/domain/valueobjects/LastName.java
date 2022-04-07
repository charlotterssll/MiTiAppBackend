package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "LASTNAME"))
public class LastName {

    private String lastName;

    public LastName(String lastName) {
        this.lastName = lastName;
    }

    protected LastName() {
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

    @Override
    public String toString() {
        return "LastName{" +
                "lastName='" + lastName + '\'' +
                '}';
    }
}
