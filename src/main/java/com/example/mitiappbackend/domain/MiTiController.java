package com.example.mitiappbackend.domain;

import com.example.mitiappbackend.domain.MiTiService;
import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.entities.MiTi;
import com.example.mitiappbackend.domain.entities.Place;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MiTiController {

    @Autowired
    private MiTiService miTiService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/test")
    public List<MiTi> getMitisList() {
        MiTi miti = new MiTi(1L, new Place(1L, new Locality("Schlöfe"), new Location("Oldenburg")),
                new Employee(1L, new FirstName("Charlotte"), new LastName("Russell")), "12:00");
        return List.of(miti);
    }

    @PostMapping("/test/addemployee")
    public void createEmployee(@RequestBody Employee employee){
        employeeService.createEmployee(employee);
    }
}
