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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
