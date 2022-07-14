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

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.place.Place;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MITI", uniqueConstraints = {
    @UniqueConstraint(columnNames = "MITI_EMPLOYEE_ID"),
    @UniqueConstraint(columnNames = "MITI_DATE")})
@NamedQueries({
    @NamedQuery(name = Miti.READ_ALL, query = "SELECT m FROM Miti m ORDER BY m.mitiId"),
    @NamedQuery(name = Miti.READ_BY_DATE_MITI_CREATOR, query = "SELECT m FROM Miti m WHERE m.date = :date AND "
            + "m.mitiCreator.abbreviation = :mitiCreator")
})
public class Miti {

    public static final String READ_ALL = "Miti.readAll";

    public static final String READ_BY_DATE_MITI_CREATOR = "Miti.readByDateMitiCreator";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITI_ID")
    private Long mitiId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MITI_PLACE_ID", referencedColumnName = "PLACE_ID")
    private Place place;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MITI_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private Employee mitiCreator;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MITI_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private List<Employee> employee;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "MITI_TIME"))
    private Time time;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "MITI_DATE"))
    private Date date;

    @JsonCreator
    public Miti(@JsonProperty("place") Place place, @JsonProperty("employeeCreator") Employee mitiCreator,
        @JsonProperty("employeeParticipants") List<Employee> employeeParticipants,
        @JsonProperty("time") Time time,
        @JsonProperty("date") Date date) {
        this.place = notNull(place, "null in place is disallowed");
        this.mitiCreator = notNull(mitiCreator, "null in employee creator is disallowed");
        this.employee = notNull(employeeParticipants, "null in employee participants is disallowed");
        this.time = notNull(time, "null in time is disallowed");
        this.date = notNull(date, "null in date is disallowed");
    }

    protected Miti() {
    }

    public Long getMitiId() {
        return mitiId;
    }

    public Place getPlace() {
        return place;
    }

    public Employee getMitiCreator() {
        return mitiCreator;
    }

    public List<Employee> getEmployeeParticipants() {
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

    public void setMitiCreator(Employee mitiCreator) {
        this.mitiCreator = notNull(mitiCreator);
    }

    public void setEmployeeParticipants(List<Employee> employeeParticipants) {
        this.employee = notNull(employeeParticipants);
    }

    public void setTime(Time time) {
        this.time = notNull(time);
    }

    public void setDate(Date date) {
        this.date = notNull(date);
    }

    public String catchMitiOnSameDay() {
        String concatString = date.getValue()
            .concat(employee.get(0).getAbbreviation().getValue());
        return concatString;
    }
}
