package com.example.mitiappbackend.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
