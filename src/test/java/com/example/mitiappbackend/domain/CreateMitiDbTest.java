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
import com.example.mitiappbackend.domain.miti.Date;
import com.example.mitiappbackend.domain.miti.Miti;
import com.example.mitiappbackend.domain.miti.Time;
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

    @DisplayName("An employee wants to create a lunch table")
    @Test
    public void testDbCreateMiti() {
        entityManager.getTransaction().begin();
        Miti mitiNew = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
            new Time("12:00"),
            new Date("2022-04-01"));
        entityManager.getTransaction().commit();

        assertThat(mitiNew.getPlace().getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(mitiNew.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(mitiNew.getPlace().getStreet().getValue()).isEqualTo("Poststraße");
        assertThat(mitiNew.getEmployee().getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(mitiNew.getEmployee().getLastName().getValue()).isEqualTo("Kranz");
        assertThat(mitiNew.getEmployee().getAbbreviation().getValue()).isEqualTo("HKR");
        assertThat(mitiNew.getTime().getValue()).isEqualTo("12:00");
        assertThat(mitiNew.getDate().getValue()).isEqualTo("2022-04-01");
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty locality")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocality() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality(""), new Location("Hannover"), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "locality must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty location")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location(""), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "location must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty street")
    @Test
    public void testDbCreateMitiIncompleteEmptyStreet() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "street must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty first name")
    @Test
    public void testDbCreateMitiIncompleteEmptyFirstName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
                new Employee(new FirstName(""), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "firstName must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty last name")
    @Test
    public void testDbCreateMitiIncompleteEmptyLastName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName(""), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "lastName must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty abbreviation")
    @Test
    public void testDbCreateMitiIncompleteEmptyAbbreviation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("")),
                new Time("12:00"),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "abbreviation must only contain capital letters and only three characters",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty time")
    @Test
    public void testDbCreateMitiIncompleteEmptyTime() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time(""),
                new Date("2022-04-01"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "time must only contain numbers in 24h time format",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete lunch table with empty date")
    @Test
    public void testDbCreateMitiIncompleteEmptyDate() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße")),
                new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
                new Time("12:00"),
                new Date(""));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "date must only contain numbers and should match YYYY-MM-DD format",
            thrown.getMessage());
    }
}
