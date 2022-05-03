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

    MiTi charlotte = new MiTi(
        new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
        new Employee(new FirstName("Charlotte"), new LastName("Russell")),
        new Time("12:00"));

    MiTi marian = new MiTi(
        new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
        new Employee(new FirstName("Marian"), new LastName("Heck")),
        new Time("12:00"));

    @Test
    public void testMiTiCharlotteNotNull() {
        entityManager.getTransaction().begin();
        entityManager.persist(charlotte);
        entityManager.getTransaction().commit();
        entityManager.clear();
        assertThat(charlotte.getMiTiId(), is(not(nullValue())));
    }

    @Test
    public void testMiTiFirstNameCharlotteIsCharlotte() {
        entityManager.getTransaction().begin();
        entityManager.persist(charlotte);
        entityManager.getTransaction().commit();
        entityManager.clear();
        assertThat(charlotte.getEmployee().getFirstName().getValue()).isEqualTo("Charlotte");
    }
}
