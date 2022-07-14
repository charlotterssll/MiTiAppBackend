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
package com.example.mitiappbackend.domain.miti;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.domain.employee.Abbreviation;
import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.employee.FirstName;
import com.example.mitiappbackend.domain.employee.LastName;
import com.example.mitiappbackend.domain.place.Locality;
import com.example.mitiappbackend.domain.place.Location;
import com.example.mitiappbackend.domain.place.Place;
import com.example.mitiappbackend.domain.place.Street;
import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class CreateMitiDbTest extends AbstractPersistenceTest {

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.clear();
    }

    @DisplayName("An employee wants to create a lunch table meeting")
    @Test
    public void testDbCreateMiti() {
        entityManager.getTransaction().begin();
        List<Employee> employee = new ArrayList<>();
        employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
            new Abbreviation("KHE")));
        Miti mitiNew = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
            employee,
            new Time("12:00"),
            new Date("2022-04-01"));
        entityManager.getTransaction().commit();

        assertThat(mitiNew.getPlace().getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(mitiNew.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(mitiNew.getPlace().getStreet().getValue()).isEqualTo("Poststraße 1a");
        assertThat(mitiNew.getMitiCreator().getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(mitiNew.getMitiCreator().getLastName().getValue()).isEqualTo("Kranz");
        assertThat(mitiNew.getMitiCreator().getAbbreviation().getValue()).isEqualTo("HKR");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getFirstName().getValue()).isEqualTo("Karl");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getLastName().getValue()).isEqualTo("Heinz");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getAbbreviation().getValue()).isEqualTo("KHE");
        assertThat(mitiNew.getTime().getValue()).isEqualTo("12:00");
        assertThat(mitiNew.getDate().getValue()).isEqualTo("2022-04-01");
    }

    @DisplayName("An employee wants to add colleagues to a lunch table meeting")
    @Test
    public void testDbCreateMitiWithColleague() {
        entityManager.getTransaction().begin();
        List<Employee> employee = new ArrayList<>();
        employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
            new Abbreviation("KHE")));
        employee.add(new Employee(new FirstName("Mika"), new LastName("Neumann"),
            new Abbreviation("MNE")));
        Miti mitiNew = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
            employee,
            new Time("12:00"),
            new Date("2022-04-01"));
        entityManager.getTransaction().commit();

        assertThat(mitiNew.getPlace().getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(mitiNew.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(mitiNew.getPlace().getStreet().getValue()).isEqualTo("Poststraße 1a");
        assertThat(mitiNew.getMitiCreator().getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(mitiNew.getMitiCreator().getLastName().getValue()).isEqualTo("Kranz");
        assertThat(mitiNew.getMitiCreator().getAbbreviation().getValue()).isEqualTo("HKR");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getFirstName().getValue()).isEqualTo("Karl");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getLastName().getValue()).isEqualTo("Heinz");
        assertThat(mitiNew.getEmployeeParticipants().get(0).getAbbreviation().getValue()).isEqualTo("KHE");
        assertThat(mitiNew.getEmployeeParticipants().get(1).getFirstName().getValue()).isEqualTo("Mika");
        assertThat(mitiNew.getEmployeeParticipants().get(1).getLastName().getValue()).isEqualTo("Neumann");
        assertThat(mitiNew.getEmployeeParticipants().get(1).getAbbreviation().getValue()).isEqualTo("MNE");
        assertThat(mitiNew.getTime().getValue()).isEqualTo("12:00");
        assertThat(mitiNew.getDate().getValue()).isEqualTo("2022-04-01");
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty locality")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocality() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality(""), new Location("Hannover"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Locality can contain different characters, upper cases and lower cases",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty location")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location(""), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Location must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty street and house number")
    @Test
    public void testDbCreateMitiIncompleteEmptyStreet() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Street must only contain letters and/or dashes and begin with upper case, it may also contain a house number",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty first name")
    @Test
    public void testDbCreateMitiIncompleteEmptyFirstName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName(""), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "FirstName must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty last name")
    @Test
    public void testDbCreateMitiIncompleteEmptyLastName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName(""),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "LastName must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty abbreviation")
    @Test
    public void testDbCreateMitiIncompleteEmptyAbbreviation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Abbreviation must only contain capital letters and only three characters",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty time")
    @Test
    public void testDbCreateMitiIncompleteEmptyTime() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time(""),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Time must only contain numbers in 24h time format,"
            + " if its a single digit number please add a leading zero, minutes 00-59, hours 00-23",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting with empty date")
    @Test
    public void testDbCreateMitiIncompleteEmptyDate() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            List<Employee> employee = new ArrayList<>();
            employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
                new Abbreviation("KHE")));
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                employee,
                new Time("12:00"),
                new Date(""));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Date must only contain numbers YYYY-MM-DD format",
            thrown.getMessage());
    }
}
