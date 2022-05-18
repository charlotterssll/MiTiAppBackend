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
package com.example.mitiappbackend.domain.miti;

import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.place.Place;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MITI")
@NamedQuery(name = Miti.READ_ALL, query = "SELECT m FROM Miti m ORDER BY m.mitiId")
public class Miti {

    public static final String READ_ALL = "Miti.readAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITI_ID")
    private Long mitiId;

    /*@Column(name = "MITI_UUID")
    private UUID uuid;*/

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MITI_PLACE_ID", referencedColumnName = "PLACE_ID")
    private Place place;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MITI_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private Employee employee;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "MITI_TIME"))
    private Time time;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "MITI_DATE"))
    private Date date;

    @JsonCreator
    public Miti(@JsonProperty("place") Place place, @JsonProperty("employee") Employee employee, @JsonProperty("time") Time time,
        @JsonProperty("date") Date date) {
        //this.uuid = UUID.randomUUID();
        this.place = notNull(place, "null in place is disallowed");
        this.employee = notNull(employee, "null in employee is disallowed");
        this.time = notNull(time, "null in time is disallowed");
        this.date = notNull(date, "null in date is disallowed");
    }

    protected Miti() {
    }

    public Long getMitiId() {
        return mitiId;
    }

    /*public UUID getUuid() {
        return uuid;
    }*/

    public Place getPlace() {
        return place;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Time getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public void setPlace(Place place) {
        this.place = notNull(place);
    }

    public void setEmployee(Employee employee) {
        this.employee = notNull(employee);
    }

    public void setTime(Time time) {
        this.time = notNull(time);
    }

    public void setDate(Date date) {
        this.date = notNull(date);
    }
}
