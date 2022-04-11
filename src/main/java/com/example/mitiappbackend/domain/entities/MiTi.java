package com.example.mitiappbackend.domain.entities;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notBlank;

@Entity
@Table(name = "MITI")
public class MiTi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITIID")
    private Long miTiID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Place place;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employees;

    @Column(name = "TIME")
    private String time;

    public MiTi(Long miTiID, Place place, Employee employees, String time) {
        this.miTiID = miTiID;
        this.place = place;
        this.employees = employees;
        this.time = time;
    }

    protected MiTi() {
    }

    public Long getMiTiID() {
        return miTiID;
    }

    public void setMiTiID(Long miTiID) {
        this.miTiID = miTiID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = notBlank(time);
    }


    public Place getPlace() {
        return place;
    }

    public Employee getEmployees() {
        return employees;
    }

}
