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
package com.example.mitiappbackend.domain.employee;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mitiappbackend.infrastructure.exceptions.EmployeeAlreadyExists;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void createEmployee(Employee employee) throws EmployeeAlreadyExists {
        List<Employee> employeeRead = employeeRepository.readEmployee();
        List<String> employeeInfos = employeeRead.stream()
            .map(Employee::employeeAlreadyExists)
            .toList();

        if (employeeInfos.contains(employee.employeeAlreadyExists())) {
            throw new EmployeeAlreadyExists();
        } else {
            employeeRepository.createEmployee(employee);
        }
    }

    @Transactional
    public List<Employee> readEmployee() {
        return employeeRepository.readEmployee();
    }

    @Transactional
    public Optional<Employee> readEmployeeByAbbreviation(Abbreviation abbreviation) throws EmployeeNotFoundException {
        Optional<Employee> employeeToRead = employeeRepository.readEmployeeByAbbreviation(abbreviation);

        if (!employeeToRead.isPresent()) {
            throw new EmployeeNotFoundException(abbreviation);
        }

        return employeeRepository.readEmployeeByAbbreviation(abbreviation);
    }

    @Transactional
    public void deleteEmployeeByAbbreviation(Abbreviation abbreviation) throws EmployeeNotFoundException {
        Optional<Employee> employeeToDelete = employeeRepository.readEmployeeByAbbreviation(abbreviation);

        if (!employeeToDelete.isPresent()) {
            throw new EmployeeNotFoundException(abbreviation);
        }
        employeeRepository.deleteEmployeeByAbbreviation(abbreviation);
    }
}
