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

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;
import com.example.mitiappbackend.domain.MiTiNotNestedRepository;
import com.example.mitiappbackend.domain.entities.MiTiNotNested;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MiTiDBTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MiTiNotNestedRepository miTiNotNestedRepository;

    MiTiNotNested charlotte = new MiTiNotNested(
        new Locality("Schlöfe"),
        new Location("Oldenburg"),
        new FirstName("Charlotte"),
        new LastName("Russell"),
        "12:00");

    MiTiNotNested marian = new MiTiNotNested(
        new Locality("Schlöfe"),
        new Location("Oldenburg"),
        new FirstName("Marian"),
        new LastName("Heck"),
        "12:00");

    @Test
    public void testMiTiFirstNameCharlotte() {
        entityManager.persist(charlotte);
        entityManager.flush();
        entityManager.clear();
        MiTiNotNested miTiNotNested = this.miTiNotNestedRepository.getById(1L);
        assertThat(miTiNotNested.getFirstName().getFirstName()).isEqualTo("Charlotte");
    }

    @Test
    public void testMiTiFirstNameMarian() {
        entityManager.persist(marian);
        entityManager.flush();
        entityManager.clear();
        MiTiNotNested miTiNotNested = this.miTiNotNestedRepository.getById(1L);
        assertThat(miTiNotNested.getFirstName().getFirstName()).isEqualTo("Marian");
        assertThat(miTiNotNested.getLocation().getLocation()).isEqualTo("Oldenburg");
    }
}
