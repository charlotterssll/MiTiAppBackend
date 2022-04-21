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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MITI")
public class MiTi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITI_ID")
    private Long miTiId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLACE_ID")
    private List<Place> place;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    private List<Employee> employee;

    @Column(name = "TIME")
    private String time;

    public MiTi(List<Place> place, List<Employee> employee, String time) {
        this.place = place;
        this.employee = employee;
        this.time = time;
    }

    protected MiTi() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = notBlank(time);
    }

    public List<Place> getPlace() {
        return place;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

}
