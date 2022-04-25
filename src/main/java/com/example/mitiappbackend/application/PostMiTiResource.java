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

import java.util.logging.Logger;

import com.example.mitiappbackend.domain.employee.EmployeeService;
import com.example.mitiappbackend.domain.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.miti.MiTi;
import com.example.mitiappbackend.domain.miti.MiTiService;

@RestController
@CrossOrigin
public class PostMiTiResource {

    private final Logger logger = Logger.getLogger(PostMiTiResource.class.getSimpleName());

    @Autowired
    private MiTiService miTiService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/mities", consumes = "application/json")
    // public ResponseEntity<MiTiDto> createMiTi(@RequestBody MiTiDto miTiDto) {
    public void createMiTi(@RequestBody MiTi miTi) {
        logger.info("RESTful call 'POST miti'");
        /*MiTi miTi = new MiTi(
                new Place(new Locality(miTiDto.getLocality()), new Location(miTiDto.getLocation())),
                new Employee(new FirstName(miTiDto.getFirstName()), new LastName(miTiDto.getLastName())),
                miTiDto.getTime());*/
        miTiService.createMiti(miTi);
        // return ResponseEntity.ok().body(miTiDto);
    }
}
