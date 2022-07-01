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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class CreatePlaceDbTest extends AbstractPersistenceTest {

    @BeforeEach
    public void beforeDbTestInsertMitiTestDataIntoDb() {
        entityManager.clear();
    }

    @DisplayName("An employee wants to create their favourite place for lunch table meetings")
    @Test
    public void testDbCreatePlace() {
        entityManager.getTransaction().begin();
        Place placeNew = new Place(new Locality("Immergrün"), new Location("Oldenburg"), new Street("Poststraße 1a"));
        entityManager.getTransaction().commit();

        assertThat(placeNew.getLocality().getValue()).isEqualTo("Immergrün");
        assertThat(placeNew.getLocation().getValue()).isEqualTo("Oldenburg");
        assertThat(placeNew.getStreet().getValue()).isEqualTo("Poststraße 1a");
    }

    @DisplayName("An employee does not want to create a place with empty locality")
    @Test
    public void testDbCreatePlaceIncompleteEmptyLocality() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Place newPlace = new Place(new Locality(""), new Location("Hannover"), new Street("Poststraße 1a"));
            entityManager.persist(newPlace);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Locality can contain different characters, upper cases and lower cases",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create a place with empty location")
    @Test
    public void testDbCreatePlaceIncompleteEmptyLocation() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Place newPlace = new Place(new Locality("Immergrün"), new Location(""), new Street("Poststraße 1a"));
            entityManager.persist(newPlace);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Location must only contain letters and/or dashes and begin with upper case",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create a place with empty street and house number")
    @Test
    public void testDbCreatePlaceIncompleteEmptyStreet() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            Place newPlace = new Place(new Locality("Immergrün"), new Location("Hannover"), new Street(""));
            entityManager.persist(newPlace);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Street must only contain letters and/or dashes and begin with upper case, it may also contain a house number",
            thrown.getMessage());
    }
}
