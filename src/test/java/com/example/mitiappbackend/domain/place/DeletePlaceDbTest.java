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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class DeletePlaceDbTest extends AbstractPersistenceTest {

    private Place place;

    @BeforeEach
    public void beforeDbTestInsertPlaceTestDataIntoDb() {
        entityManager.getTransaction().begin();
        place = new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a"));
        entityManager.persist(place);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to delete an existing place")
    @Test
    public void testDbDeletePlace() {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(place));
        entityManager.getTransaction().commit();
        entityManager.clear();

        MatcherAssert.assertThat(entityManager.find(Place.class, place.getPlaceId()), is(nullValue()));
    }
}
