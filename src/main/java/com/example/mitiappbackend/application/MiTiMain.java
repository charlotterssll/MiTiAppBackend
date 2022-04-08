package com.example.mitiappbackend.application;

import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiTiMain {

    public static void main(String[] args) {

        SpringApplication.run(MiTiMain.class, args);

        Employee employee = new Employee(new FirstName("Charlotte"), new LastName("Russell"));
        System.out.println(employee.getFirstName());
        System.out.println(employee.getLastName());
    }

}
