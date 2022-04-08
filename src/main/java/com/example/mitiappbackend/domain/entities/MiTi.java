package com.example.mitiappbackend.domain.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "MITI")
public class MiTi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITIID")
    private Long miTiID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "miti_employee_id", referencedColumnName = "employeeid")
    private Employee employees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "miti_place_id", referencedColumnName = "placeid")
    private Place place;

    @Column(name = "TIME")
    private String time;

    public MiTi(Employee employees, Place place, String time) {
        this.employees = employees;
        this.place = place;
        this.time = time;
    }

    protected MiTi() {
    }

    public void setMiTiID(Long miTiID) {
        this.miTiID = miTiID;
    }

    public Long getMiTiID() {
        return miTiID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = notBlank(time);
    }

    public Employee getEmployees() {
        return employees;
    }

    public void setEmployees(Employee employees) {
        this.employees = notNull(employees);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = notNull(place);
    }
}
