package com.example.mitiappbackend.domain.employee;

import com.example.mitiappbackend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
