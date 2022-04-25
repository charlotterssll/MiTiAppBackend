package com.example.mitiappbackend.domain.place;

import com.example.mitiappbackend.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
