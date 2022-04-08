package com.example.mitiappbackend.application;

import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.entities.MiTi;
import com.example.mitiappbackend.domain.entities.Place;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class MiTiController {

    @GetMapping(value = "/test", produces = "application/json")
    public MiTi getEmployeeList() {
       return new MiTi(new Employee(new FirstName("Charlotte"), new LastName("Russell")),
                new Place(new Locality("Schlöfe"), new Location("Oldenburg")), "");
    }
}
