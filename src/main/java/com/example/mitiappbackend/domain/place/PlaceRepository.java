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

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepository {

    @Autowired
    private EntityManager entityManager;

    public void createPlace(Place place) {
        entityManager.persist(place);
    }

    @Transactional
    public List<Place> readPlaces() {
        return entityManager.createNamedQuery(Place.READ_ALL, Place.class).getResultList();
    }

    @Transactional
    public Optional<Place> readPlaceByStreet(Street street) {
        return entityManager.createNamedQuery(Place.READ_BY_PLACE_STREET, Place.class)
            .setParameter("street", street)
            .getResultList().stream().findAny();
    }

    @Transactional
    public void deletePlaceByStreet(Street street) {
        Optional<Place> placeToDelete = this.readPlaceByStreet(street);
        if (placeToDelete.isPresent()) {
            entityManager.remove(placeToDelete.get());
        }
    }
}
