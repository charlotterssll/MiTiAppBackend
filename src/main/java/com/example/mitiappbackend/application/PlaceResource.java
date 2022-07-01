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
package com.example.mitiappbackend.application;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.place.Place;
import com.example.mitiappbackend.domain.place.PlaceService;
import com.example.mitiappbackend.infrastructure.exceptions.PlaceNotFoundException;

@RestController
@CrossOrigin
public class PlaceResource {

    private static final Logger LOGGER = Logger.getLogger(PlaceResource.class.getSimpleName());

    @Autowired
    private PlaceService placeService;

    @PostMapping(value = "/place", consumes = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void createPlace(@RequestBody Place place) {
        placeService.createPlace(place);
        LOGGER.info("RESTful call 'POST place'");
    }

    @GetMapping(value = "/place", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Place> readPlace() {
        LOGGER.info("RESTful call 'GET place'");
        return placeService.readPlace();
    }

    @GetMapping(value = "/place/{placeId}", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Place readPlaceById(@PathVariable Long placeId) throws PlaceNotFoundException {
        LOGGER.info("RESTful call 'GET place by placeId'");
        return placeService.readPlaceById(placeId);
    }

    @DeleteMapping(value = "/place/{placeId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deletePlaceById(@PathVariable Long placeId) throws PlaceNotFoundException {
        placeService.deletePlaceById(placeId);
        LOGGER.info("RESTful call 'DELETE place'");
    }
}
