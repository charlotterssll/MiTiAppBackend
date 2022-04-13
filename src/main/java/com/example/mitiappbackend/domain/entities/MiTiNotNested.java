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
package com.example.mitiappbackend.domain.entities;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;

@Entity
@Table(name = "MITINOTNESTED")
public class MiTiNotNested {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITINOTNESTEDID")
    private Long miTiNotNestedId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "LOCALITY"))
    private Locality locality;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "LOCATION"))
    private Location location;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "FIRSTNAME"))
    private FirstName firstName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "LASTNAME"))
    private LastName lastName;

    @Column(name = "TIME")
    private String time;

    public MiTiNotNested(Locality locality, Location location, FirstName firstName, LastName lastName, String time) {
        this.locality = notNull(locality);
        this.location = notNull(location);
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
        this.time = notNull(time);
    }

    protected MiTiNotNested() {
    }

    public Locality getLocality() {
        return locality;
    }

    public Location getLocation() {
        return location;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = notBlank(time);
    }
}
