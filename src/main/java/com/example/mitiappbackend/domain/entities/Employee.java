package com.example.mitiappbackend.domain.entities;

import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEEID")
    private Long employeeID;

    @Embedded
    private FirstName firstName;

    @Embedded
    private LastName lastName;

    public Employee(FirstName firstName, LastName lastName) {
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }

    protected Employee() {
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = notNull(employeeID);
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = notNull(firstName);
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = notNull(lastName);
    }
}
