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

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mitiappbackend.infrastructure.exceptions.PlaceAlreadyExists;
import com.example.mitiappbackend.infrastructure.exceptions.PlaceNotFoundException;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Transactional
    public void createPlace(Place place) throws PlaceAlreadyExists {
        List<Place> placeRead = placeRepository.readPlaces();
        List<String> placeInfos = placeRead.stream()
            .map(Place::placeAlreadyExists)
            .toList();

        if (placeInfos.contains(place.placeAlreadyExists())) {
            throw new PlaceAlreadyExists();
        } else {
            placeRepository.createPlace(place);
        }
    }

    @Transactional
    public List<Place> readPlace() {
        return placeRepository.readPlaces();
    }

    @Transactional
    public Place readPlaceById(Long placeId) throws PlaceNotFoundException {
        Place placeRead = placeRepository.readPlaceById(placeId);

        if (placeRead == null) {
            throw new PlaceNotFoundException(placeId);
        }

        return placeRepository.readPlaceById(placeId);
    }

    @Transactional
    public void deletePlaceById(Long placeId) throws PlaceNotFoundException {
        Place placeDelete = placeRepository.readPlaceById(placeId);

        if (placeDelete == null) {
            throw new PlaceNotFoundException(placeId);
        }

        placeRepository.deletePlaceById(placeId);
    }
}
