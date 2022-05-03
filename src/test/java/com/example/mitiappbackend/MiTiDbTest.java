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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.mitiappbackend.domain.employee.Employee;
import com.example.mitiappbackend.domain.employee.FirstName;
import com.example.mitiappbackend.domain.employee.LastName;
import com.example.mitiappbackend.domain.miti.MiTi;
import com.example.mitiappbackend.domain.miti.MiTiRepository;
import com.example.mitiappbackend.domain.miti.Time;
import com.example.mitiappbackend.domain.place.Locality;
import com.example.mitiappbackend.domain.place.Location;
import com.example.mitiappbackend.domain.place.Place;

@DataJpaTest
public class MiTiDbTest {

    MiTi charlotte = new MiTi(
        new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
        new Employee(new FirstName("Charlotte"), new LastName("Russell")),
        new Time("12:00"));

    MiTi marian = new MiTi(
        new Place(new Locality("Schlöfe"), new Location("Oldenburg")),
        new Employee(new FirstName("Marian"), new LastName("Heck")),
        new Time("12:00"));

    @Autowired
    private MiTiRepository miTiRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testMiTiFirstNameCharlotte() {
        entityManager.persist(charlotte);
        entityManager.flush();
        entityManager.clear();
        MiTi miTi = this.miTiRepository.getById(1L);
        assertThat(miTi.getEmployee().getFirstName().getValue()).isEqualTo("Charlotte");
    }

    @Test
    public void testMiTiFirstNameMarian() {
        entityManager.persist(marian);
        entityManager.flush();
        entityManager.clear();
        MiTi miTi = this.miTiRepository.getById(2L);
        assertThat(miTi.getEmployee().getFirstName().getValue()).isEqualTo("Marian");
        assertThat(miTi.getPlace().getLocation().getValue()).isEqualTo("Oldenburg");
    }
}
