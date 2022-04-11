package com.example.mitiappbackend.domain.valueobjects;

import javax.persistence.Embeddable;

import static org.apache.commons.lang3.Validate.notBlank;

@Embeddable
public class LastName {

    private String lastName;

    public LastName(String lastName) {
        this.lastName = notBlank(lastName);
    }

    protected LastName() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
