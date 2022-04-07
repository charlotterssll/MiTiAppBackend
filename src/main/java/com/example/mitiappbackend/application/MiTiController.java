package com.example.mitiappbackend.application;

import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.entities.MiTi;
import com.example.mitiappbackend.domain.entities.StringResponse;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiTiController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/hello")
    public JSONObject hello() {
        JSONObject obj = new JSONObject();

        obj.put("name", "foo");

        return obj;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/test")
    public Employee getMiTI() {
        return new Employee(new FirstName("Charlotte"), new LastName("Russell"));
    }
}
