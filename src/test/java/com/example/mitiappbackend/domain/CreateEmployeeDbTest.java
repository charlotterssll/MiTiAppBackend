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
package com.example.mitiappbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.domain.employee.Abbreviation;
import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.employee.FirstName;
import com.example.mitiappbackend.domain.employee.LastName;
import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class CreateEmployeeDbTest extends AbstractPersistenceTest {

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.clear();
    }

    @DisplayName("An employee wants to create an employee")
    @Test
    public void testDbCreateEmployee() {
        entityManager.getTransaction().begin();
        Employee newEmployee = new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR"));
        entityManager.getTransaction().commit();

        assertThat(newEmployee.getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(newEmployee.getLastName().getValue()).isEqualTo("Kranz");
        assertThat(newEmployee.getAbbreviation().getValue()).isEqualTo("HKR");
    }

    @DisplayName("An employee does not want to create an employee with empty first name")
    @Test
    public void testDbCreateEmployeeIncompleteEmptyFirstName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Employee newEmployee = new Employee(new FirstName(""), new LastName("Kranz"), new Abbreviation("HKR"));
            entityManager.persist(newEmployee);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "FirstName must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an employee with empty last name")
    @Test
    public void testDbCreateEmployeeIncompleteEmptyLastName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Employee newEmployee = new Employee(new FirstName("Hannelore"), new LastName(""), new Abbreviation("HKR"));
            entityManager.persist(newEmployee);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "LastName must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an employee with empty abbreviation")
    @Test
    public void testDbCreateEmployeeIncompleteEmptyAbbreviation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Employee newEmployee = new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation(""));
            entityManager.persist(newEmployee);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Abbreviation must only contain capital letters and only three characters",
            thrown.getMessage());
    }
}
