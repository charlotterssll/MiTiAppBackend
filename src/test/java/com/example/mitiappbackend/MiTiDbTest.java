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
import com.example.mitiappbackend.domain.miti.MiTi;
import com.example.mitiappbackend.domain.miti.Time;
import com.example.mitiappbackend.domain.place.Locality;
import com.example.mitiappbackend.domain.place.Location;
import com.example.mitiappbackend.domain.place.Place;
public class MiTiDbTest extends AbstractPersistenceTest {

    private MiTi miTi;

    @BeforeEach
    public void insertMiTiTestdata() {
        entityManager.getTransaction().begin();
        miTi = new MiTi(
            new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
            new Employee(new FirstName("Charlotte"), new LastName("Russell")),
            new Time("12:00"));
        entityManager.persist(miTi);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void testMiTiNotNull() {
        entityManager.getTransaction().begin();
        MiTi newMiTi = new MiTi(
            new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
            new Employee(new FirstName("Marian"), new LastName("Heck")),
            new Time("12:00"));
        entityManager.persist(newMiTi);
        entityManager.getTransaction().commit();

        assertThat(newMiTi.getMiTiId(), is(not(nullValue())));
    }

    @Test
    public void testFirstNameMarianIsMarian() {
        entityManager.getTransaction().begin();
        MiTi marian = new MiTi(
            new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
            new Employee(new FirstName("Marian"), new LastName("Heck")),
            new Time("12:00"));
        entityManager.getTransaction().commit();

        assertThat(marian.getEmployee().getFirstName().getValue()).isEqualTo("Marian");
    }

    @Test
    public void testRemoveMiTi() {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(miTi));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertThat(entityManager.find(MiTi.class, miTi.getMiTiId()), is(nullValue()));
    }

    @Test
    public void testEditMiTiLocationIsBaecker() {
        entityManager.getTransaction().begin();
        MiTi foundMiTi = entityManager.find(MiTi.class, miTi.getMiTiId());
        foundMiTi.setPlace(new Place(new Locality("Baecker"), new Location("Oldenburg")));
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertThat(entityManager.find(MiTi.class, miTi.getMiTiId()).getPlace().getLocality().getValue(), is("Baecker"));
    }
}
