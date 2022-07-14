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

public class ReadMitiDbTest extends AbstractPersistenceTest {

    private Miti miti;

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.getTransaction().begin();
        List<Employee> employee = new ArrayList<>();
        employee.add(new Employee(new FirstName("Karl"), new LastName("Heinz"),
            new Abbreviation("KHE")));
        miti = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz"), new Abbreviation("HKR")),
            employee,
            new Time("12:00"),
            new Date("2022-04-01"));
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to read information about an already existing lunch table meeting")
    @Test
    public void testDbReadMiti() {
        assertThat(miti.getPlace().getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(miti.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(miti.getPlace().getStreet().getValue()).isEqualTo("Poststraße 1a");
        assertThat(miti.getMitiCreator().getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(miti.getMitiCreator().getLastName().getValue()).isEqualTo("Kranz");
        assertThat(miti.getMitiCreator().getAbbreviation().getValue()).isEqualTo("HKR");
        assertThat(miti.getEmployeeParticipants().get(0).getFirstName().getValue()).isEqualTo("Karl");
        assertThat(miti.getEmployeeParticipants().get(0).getLastName().getValue()).isEqualTo("Heinz");
        assertThat(miti.getEmployeeParticipants().get(0).getAbbreviation().getValue()).isEqualTo("KHE");
        assertThat(miti.getTime().getValue()).isEqualTo("12:00");
        assertThat(miti.getDate().getValue()).isEqualTo("2022-04-01");
    }
}
