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
package com.example.mitiappbackend.domain.place;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class ReadPlaceDbTest extends AbstractPersistenceTest {

    private Place place;

    @BeforeEach
    public void beforeDbTestInsertPlaceTestDataIntoDb() {
        entityManager.getTransaction().begin();
        place = new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a"));
        entityManager.persist(place);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to read information about an already existing place")
    @Test
    public void testDbReadPlace() {
        assertThat(place.getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(place.getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(place.getStreet().getValue()).isEqualTo("Poststraße 1a");
    }
}
