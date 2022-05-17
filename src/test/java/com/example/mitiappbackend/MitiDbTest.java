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
package com.example.mitiappbackend;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.employee.FirstName;
import com.example.mitiappbackend.domain.employee.LastName;
import com.example.mitiappbackend.domain.miti.Miti;
import com.example.mitiappbackend.domain.miti.Time;
import com.example.mitiappbackend.domain.place.Locality;
import com.example.mitiappbackend.domain.place.Location;
import com.example.mitiappbackend.domain.place.Place;

public class MitiDbTest extends AbstractPersistenceTest {

    private Miti miti;

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.getTransaction().begin();
        miti = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz")),
            new Time("14:30"));
        entityManager.persist(miti);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("Employee wants to create a lunch table")
    @Test
    public void testDbCreateMiti() {
        entityManager.getTransaction().begin();
        Miti mitiNew = new Miti(
            new Place(new Locality("Metzger"), new Location("Hannover")),
            new Employee(new FirstName("Karl"), new LastName("Heinz")),
            new Time("12:00"));
        entityManager.getTransaction().commit();

        assertThat(mitiNew.getPlace().getLocality().getValue()).isEqualTo("Metzger");
        assertThat(mitiNew.getPlace().getLocation().getValue()).isEqualTo("Hannover");
        assertThat(mitiNew.getEmployee().getFirstName().getValue()).isEqualTo("Karl");
        assertThat(mitiNew.getEmployee().getLastName().getValue()).isEqualTo("Heinz");
        assertThat(mitiNew.getTime().getValue()).isEqualTo("12:00");
    }

    @DisplayName("Employee does not want to create an incomplete lunch table with empty locality")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocality() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality(""), new Location("Hannover")),
                new Employee(new FirstName("Karl"), new LastName("Heinz")),
                new Time("12:00"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "locality must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("Employee does not want to create an incomplete lunch table with empty location")
    @Test
    public void testDbCreateMitiIncompleteEmptyLocation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Metzger"), new Location("")),
                new Employee(new FirstName("Karl"), new LastName("Heinz")),
                new Time("12:00"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "location must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("Employee does not want to create an incomplete lunch table with empty first name")
    @Test
    public void testDbCreateMitiIncompleteEmptyFirstName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Metzger"), new Location("Hannover")),
                new Employee(new FirstName(""), new LastName("Heinz")),
                new Time("12:00"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "firstName must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("Employee does not want to create an incomplete lunch table with empty last name")
    @Test
    public void testDbCreateMitiIncompleteEmptyLastName() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Metzger"), new Location("Hannover")),
                new Employee(new FirstName("Karl"), new LastName("")),
                new Time("12:00"));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "lastName must only contain letters and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("Employee does not want to create an incomplete lunch table with empty time")
    @Test
    public void testDbCreateMitiIncompleteEmptyTime() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Miti newMiti = new Miti(
                new Place(new Locality("Metzger"), new Location("Hannover")),
                new Employee(new FirstName("Karl"), new LastName("Heinz")),
                new Time(""));
            entityManager.persist(newMiti);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "time must only contain numbers in 24h time format",
            thrown.getMessage());
    }

    @DisplayName("Employee wants to read information about already existing lunch tables")
    @Test
    public void testDbGetMiti() {
        assertThat(miti.getPlace().getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(miti.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(miti.getEmployee().getFirstName().getValue()).isEqualTo("Hannelore");
        assertThat(miti.getEmployee().getLastName().getValue()).isEqualTo("Kranz");
        assertThat(miti.getTime().getValue()).isEqualTo("14:30");
    }

    @DisplayName("Employee wants to update the locality on an existing lunch table")
    @Test
    public void testDbPutMitiLocality() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Metzger"), new Location("Oldenburg")));
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Kranz")));
        foundMiti.setTime(new Time("14:30"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Metzger"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Oldenburg"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Kranz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
    }

    @DisplayName("Employee wants to update the location on an existing lunch table")
    @Test
    public void testDbPutMitiLocation() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Hannover")));
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Kranz")));
        foundMiti.setTime(new Time("14:30"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Hannover"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Kranz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
    }

    @DisplayName("Employee wants to update the first name on an existing lunch table")
    @Test
    public void testDbPutMitiFirstName() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Oldenburg")));
        foundMiti.setEmployee(new Employee(new FirstName("Karl"), new LastName("Kranz")));
        foundMiti.setTime(new Time("14:30"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Oldenburg"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Karl"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Kranz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
    }

    @DisplayName("Employee wants to update the last name on an existing lunch table")
    @Test
    public void testDbPutMitiLastName() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Oldenburg")));
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Heinz")));
        foundMiti.setTime(new Time("14:30"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Oldenburg"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Heinz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("14:30"));
    }

    @DisplayName("Employee wants to update the time on an existing lunch table")
    @Test
    public void testDbPutMitiTime() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Immergrün"), new Location("Oldenburg")));
        foundMiti.setEmployee(new Employee(new FirstName("Hannelore"), new LastName("Kranz")));
        foundMiti.setTime(new Time("12:00"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Immergrün"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Oldenburg"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Hannelore"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Kranz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("12:00"));
    }

    @DisplayName("Employee wants to update all information on an existing lunch table")
    @Test
    public void testDbPutMitiAll() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Metzger"), new Location("Hannover")));
        foundMiti.setEmployee(new Employee(new FirstName("Karl"), new LastName("Heinz")));
        foundMiti.setTime(new Time("12:00"));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Metzger"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocation().getValue(), is("Hannover"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getFirstName().getValue(), is("Karl"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getEmployee().getLastName().getValue(), is("Heinz"));
        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()).getTime().getValue(), is("12:00"));
    }

    @DisplayName("Employee wants to delete an existing lunch table")
    @Test
    public void testDbDeleteMiti() {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(miti));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Miti.class, miti.getMitiId()), is(nullValue()));
    }
}
