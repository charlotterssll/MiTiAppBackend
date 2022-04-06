package com.example.mitiappbackend.domain;

import javax.persistence.*;

@Entity
@Table(name = "MITI")
public class MiTi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITIID")
    private Long miTiID;

    @Column(name = "LOCALITY")
    private String locality;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "TIME")
    private String time;

    public MiTi(String locality, String location, String firstName, String lastName, String time) {
        this.locality = locality;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.time = time;
    }

    public MiTi() {
    }

    public Long getMiTiID() {
        return miTiID;
    }

    public void setMiTiID(Long miTiID) {
        this.miTiID = miTiID;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MiTi{" +
                "miTiID=" + miTiID +
                ", locality='" + locality + '\'' +
                ", location='" + location + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", time=" + time +
                '}';
    }
}
