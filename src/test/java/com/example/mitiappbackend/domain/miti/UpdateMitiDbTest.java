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

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
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

public class UpdateMitiDbTest extends AbstractPersistenceTest {

    private Miti miti;

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.getTransaction().begin();
        miti = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
            new Time("12:00"),
            new Date("2022-04-01"));
        entityManager.persist(miti);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to update the locality on an existing lunch table")
    @Test
    public void testDbUpdateMitiLocality() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Metzger"), new Location("Oldenburg"), new Street("Poststraße 1a")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Metzger"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Oldenburg"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getStreet().getValue(), is("Poststraße 1a"));
    }

    @DisplayName("An employee wants to update the location on an existing lunch table")
    @Test
    public void testDbUpdateMitiLocation() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Essen"), new Street("Poststraße")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Essen"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getStreet().getValue(), is("Poststraße"));
    }

    @DisplayName("An employee wants to update the street on an existing lunch table")
    @Test
    public void testDbUpdateMitiStreet() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Essen"), new Street("Buchstraße")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Essen"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getStreet().getValue(), is("Buchstraße"));
    }

    @DisplayName("An employee wants to update the first name on an existing lunch table")
    @Test
    public void testDbUpdateMitiFirstName() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setEmployee(new Employee(new FirstName("Karl"), new LastName("Kranz"), new Abbreviation("HKR")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Karl"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Kranz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getAbbreviation().getValue(), is("HKR"));
    }

    @DisplayName("An employee wants to update the last name on an existing lunch table")
    @Test
    public void testDbUpdateMitiLastName() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Heinz"), new Abbreviation("HKR")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Heinz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getAbbreviation().getValue(), is("HKR"));
    }

    @DisplayName("An employee wants to update the abbreviation on an existing lunch table")
    @Test
    public void testDbUpdateMitiAbbreviation() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Heinz"), new Abbreviation("KHE")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Heinz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getAbbreviation().getValue(), is("KHE"));
    }

    @DisplayName("An employee wants to update the time on an existing lunch table")
    @Test
    public void testDbUpdateMitiTime() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setTime(new Time("14:30"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
    }

    @DisplayName("An employee wants to update the date on an existing lunch table")
    @Test
    public void testDbUpdateMitiDate() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setDate(new Date("2022-05-01"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getDate().getValue(), is("2022-05-01"));
    }

    @DisplayName("An employee wants to update all information on an existing lunch table")
    @Test
    public void testDbUpdateMitiAll() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Metzger"), new Location("Essen"), new Street("Buchstraße")));
        foundMiti.setEmployee(new Employee(new FirstName("Karl"), new LastName("Heinz"), new Abbreviation("KHE")));
        foundMiti.setTime(new Time("14:30"));
        foundMiti.setDate(new Date("2022-05-01"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Metzger"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Essen"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getStreet().getValue(), is("Buchstraße"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Karl"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Heinz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getAbbreviation().getValue(), is("KHE"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getDate().getValue(), is("2022-05-01"));
    }
}
