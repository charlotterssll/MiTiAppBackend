package com.example.mitiappbackend.domain.entities;

import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "EMPLOYEE")
@NamedQuery(name = Employee.FIND_ALL, query = "SELECT c FROM Employee c")
public class Employee {

    public static final String FIND_ALL = "Employee.findAll";

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

    public Employee(FirstName firstName, LastName lastName) {
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }

    protected Employee() {
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }
}
