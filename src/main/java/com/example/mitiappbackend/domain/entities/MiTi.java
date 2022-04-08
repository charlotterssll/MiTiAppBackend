package com.example.mitiappbackend.domain.entities;

import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "MITI")
public class MiTi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MITIID")
    private Long miTiID;

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

    public MiTi(Locality locality, Location location, FirstName firstName, LastName lastName, String time) {
        this.locality = notNull(locality);
        this.location = notNull(location);
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
        this.time = notNull(time);
    }

    protected MiTi() {
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

}
