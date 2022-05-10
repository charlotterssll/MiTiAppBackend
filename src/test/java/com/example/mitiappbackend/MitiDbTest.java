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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
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
    public void insertMitiTestdata() {
        entityManager.getTransaction().begin();
        miti = new Miti(
            new Place(new Locality("Immergrün"), new Location("Oldenburg")),
            new Employee(new FirstName("Hannelore"), new LastName("Kranz")),
            new Time("14:30"));
        entityManager.persist(miti);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void testMitiNotNull() {
        entityManager.getTransaction().begin();
        Miti newMiti = new Miti(
            new Place(new Locality("Metzger"), new Location("Hannover")),
            new Employee(new FirstName("Karl"), new LastName("Heinz")),
            new Time("12:00"));
        entityManager.persist(newMiti);
        entityManager.getTransaction().commit();

        assertThat(newMiti.getMitiId(), is(not(nullValue())));
    }

    @Test
    public void testFirstNameKarlIsKarl() {
        entityManager.getTransaction().begin();
        Miti mitiNew = new Miti(
            new Place(new Locality("Metzger"), new Location("Hannover")),
            new Employee(new FirstName("Karl"), new LastName("Heinz")),
            new Time("12:00"));
        entityManager.getTransaction().commit();

        assertThat(mitiNew.getEmployee().getFirstName().getValue()).isEqualTo("Karl");
    }

    @Test
    public void testRemoveMiti() {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(miti));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertThat(entityManager.find(Miti.class, miti.getMitiId()), is(nullValue()));
    }

    @Test
    public void testEditMitiLocationIsBaecker() {
        entityManager.getTransaction().begin();
        Miti foundMiti = entityManager.find(Miti.class, miti.getMitiId());
        foundMiti.setPlace(new Place(new Locality("Baecker"), new Location("Oldenburg")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertThat(entityManager.find(Miti.class, miti.getMitiId()).getPlace().getLocality().getValue(), is("Baecker"));
    }
}
