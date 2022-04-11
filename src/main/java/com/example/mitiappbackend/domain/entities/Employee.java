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
    @AttributeOverride(name = "value", column = @Column(name = "FIRSTNAME"))
    private FirstName firstName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "LASTNAME"))
    private LastName lastName;

    public Employee(Long employeeID,FirstName firstName, LastName lastName) {
        this.employeeID = notNull(employeeID);
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }

    protected Employee() {
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

}
