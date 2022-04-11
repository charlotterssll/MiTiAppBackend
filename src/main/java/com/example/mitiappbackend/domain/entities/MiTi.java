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
    private Employee employee;

    @Column(name = "TIME")
    private String time;

    public MiTi(Place place, Employee employee, String time) {
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


    public Place getPlace() {
        return place;
    }

    public Employee getEmployee() {
        return employee;
    }

}
