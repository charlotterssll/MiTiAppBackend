/*
 * Copyright \(C\) open knowledge GmbH\.
 *
 * Licensed under the Apache License, Version 2\.0 \(the "License"\);
 * you may not use this file except in compliance with the License\.
 * You may obtain a copy of the License at
 *
 *     http://www\.apache\.org/licenses/LICENSE-2\.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied\.
 * See the License for the specific language governing permissions and
 * limitations under the License\.
 */
package com.example.mitiappbackend.domain.employee;

import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EMPLOYEE_FIRSTNAME"))
    private FirstName firstName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EMPLOYEE_LASTNAME"))
    private LastName lastName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EMPLOYEE_ABBREVIATION"))
    private Abbreviation abbreviation;

    @JsonCreator
    public Employee(@JsonProperty("firstName") FirstName firstName,
        @JsonProperty("lastName") LastName lastName,
        @JsonProperty("abbreviation") Abbreviation abbreviation) {
        this.firstName = notNull(firstName, "null in firstName is disallowed");
        this.lastName = notNull(lastName, "null in lastName is disallowed");
        this.abbreviation = notNull(abbreviation, "null in abbreviation is disallowed");
    }

    protected Employee() {
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Abbreviation getAbbreviation() {
        return abbreviation;
    }
}
