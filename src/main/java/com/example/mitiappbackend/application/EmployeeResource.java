/*
 * Copyright \(C\) open knowledge GmbH\.
 *
 * Licensed under the Apache License, Version 2\.0 \(the "License"\);
 * you may not use this file except in compliance with the License\.
 * You may obtain a copy of the License at
 *
 *     http://www\.apache\.org/licenses/LICENSE-2\.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied\.
 * See the License for the specific language governing permissions and
 * limitations under the License\.
 */
package com.example.mitiappbackend.application;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.employee.EmployeeService;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeAlreadyExists;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeNotFoundException;

@RestController
@CrossOrigin
public class EmployeeResource {

    private static final Logger LOGGER = Logger.getLogger(EmployeeResource.class.getSimpleName());

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/employee", consumes = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void createEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExists {
        employeeService.createEmployee(employee);
        LOGGER.info("RESTful call 'POST employee'");
    }

    @GetMapping(value = "/employee", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Employee> readEmployee() {
        LOGGER.info("RESTful call 'GET employee'");
        return employeeService.readEmployee();
    }

    @GetMapping(value = "/employee/{employeeId}", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Employee readEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        LOGGER.info("RESTful call 'GET employee by employeeId'");
        return employeeService.readEmployeeById(employeeId);
    }

    @DeleteMapping(value = "/employee/{employeeId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeById(employeeId);
        LOGGER.info("RESTful call 'DELETE employee'");
    }
}
