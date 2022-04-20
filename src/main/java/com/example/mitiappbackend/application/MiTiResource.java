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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.entities.Employee;
import com.example.mitiappbackend.domain.entities.MiTi;
import com.example.mitiappbackend.domain.entities.MiTiNotNested;
import com.example.mitiappbackend.domain.entities.Place;
import com.example.mitiappbackend.domain.valueobjects.FirstName;
import com.example.mitiappbackend.domain.valueobjects.LastName;
import com.example.mitiappbackend.domain.valueobjects.Locality;
import com.example.mitiappbackend.domain.valueobjects.Location;

@RestController
@CrossOrigin
public class MiTiResource {

    MiTi miTi = new MiTi(
        new Place(new Locality("Schloefe"), new Location("Oldenburg")),
        new Employee(new FirstName("Charlotte"), new LastName("Russell")),
        "12:00"
    );

    private final Logger logger = Logger.getLogger(MiTiResource.class.getSimpleName());

    @Autowired
    private MiTiNotNestedService miTiNotNestedService;

    @GetMapping(value = "/mities", produces = "application/json")
    public List<MiTiNotNested> getMiTisNotNested() {
        logger.info("RESTful call 'GET mities'");
        return miTiNotNestedService.getMiTiesNotNested();
    }

    @PostMapping(value = "/mities/addmiti", consumes = "application/json")
    public void createMiTiNotNested(@RequestBody MiTiNotNested miTiNotNested) {
        logger.info("RESTful call 'POST miti'");
        miTiNotNestedService.createMitiNotNested(miTiNotNested);
    }

    /*@Autowired
    private MiTiService miTiService;

    @GetMapping(value = "/mities", produces = "application/json")
    public List<MiTi> getMiTisNotNested() {
        logger.info("RESTful call 'GET mities'");
        return miTiService.getMiTies();
        //return List.of(miTi);
    }

    @PostMapping(value = "/mities/addmiti", consumes = "application/json")
    public void createMiTiNotNested(@RequestBody MiTi miTi) {
        logger.info("RESTful call 'POST miti'");
        miTiService.createMiti(miTi);
    }*/
}
