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
    public Employee readEmployeeById(Long employeeId) {
        return entityManager.find(Employee.class, employeeId);
    }

    @Transactional
    public void deleteEmployeeById(Long employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        entityManager.remove(employee);
    }
}
