package com.example.mitiappbackend.domain.place;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> getPlaces() {
        return placeRepository.findAll();
    }

    public void createPlace(Place place) {
        placeRepository.save(place);
    }
}
