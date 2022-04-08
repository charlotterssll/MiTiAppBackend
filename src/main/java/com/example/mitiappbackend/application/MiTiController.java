package com.example.mitiappbackend.application;

import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Employee> getEmployeeList() {
        return List.of(new Employee(new FirstName("Charlotte"), new LastName("Russell")));
    }
}
