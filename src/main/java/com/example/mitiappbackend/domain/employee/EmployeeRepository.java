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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    @Autowired
    private EntityManager entityManager;

    public void createEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Transactional
    public List<Employee> readEmployee() {
        return entityManager.createNamedQuery(Employee.READ_ALL, Employee.class).getResultList();
    }

    @Transactional
    public Optional<Employee> readEmployeeByAbbreviation(Abbreviation abbreviation) {
        return entityManager.createNamedQuery(Employee.READ_BY_EMPLOYEE_ABBREVIATION, Employee.class)
            .setParameter("abbreviation", abbreviation)
            .getResultList().stream().findAny();
    }

    @Transactional
    public void deleteEmployeeByAbbreviation(Abbreviation abbreviation) {
        Optional<Employee> employeeToDelete = this.readEmployeeByAbbreviation(abbreviation);
        if (employeeToDelete.isPresent()) {
            entityManager.remove(employeeToDelete.get());
        }
        entityManager.remove(employeeToDelete.get());
    }
}
