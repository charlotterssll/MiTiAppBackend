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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.employee.Abbreviation;
import com.example.mitiappbackend.domain.miti.Date;
import com.example.mitiappbackend.domain.miti.Miti;
import com.example.mitiappbackend.domain.miti.MitiService;
import com.example.mitiappbackend.domain.miti.MitiValues;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeNotRegisteredException;
import com.example.mitiappbackend.infrastructure.exceptions.MitiCatchMoreThanFiveEmployees;
import com.example.mitiappbackend.infrastructure.exceptions.MitiCatchOnSameDayException;
import com.example.mitiappbackend.infrastructure.exceptions.MitiNotFoundException;

@RestController
@CrossOrigin
public class MitiResource {

    private static final Logger LOGGER = Logger.getLogger(MitiResource.class.getSimpleName());

    @Autowired
    private MitiService mitiService;

    @PostMapping(value = "/miti", consumes = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void createMiti(@RequestBody Miti miti) throws MitiCatchOnSameDayException,
            EmployeeNotRegisteredException, MitiCatchMoreThanFiveEmployees {
        mitiService.createMiti(miti);
        LOGGER.info("RESTful call 'POST miti'");
    }

    @GetMapping(value = "/miti", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Miti> readMiti() {
        LOGGER.info("RESTful call 'GET miti'");
        return mitiService.readMiti();
    }

    @GetMapping(value = "/miti/{date}/{mitiCreator}", produces = "application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MitiValues readMitiByDateMitiCreator(@PathVariable(value = "date") Date date,
        @PathVariable(value = "mitiCreator") Abbreviation mitiCreator) throws MitiNotFoundException {
        LOGGER.info("RESTful call 'GET miti by date and mitiCreator'");
        return mitiService.readMitiByDateMitiCreator(date, mitiCreator)
            .map(MitiValues::new).orElseThrow(() -> new MitiNotFoundException(date, mitiCreator));
    }

    @PutMapping(value = "/miti/{date}/{mitiCreator}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void updateMitiByDateMitiCreator(@PathVariable(value = "date") Date date,
        @PathVariable(value = "mitiCreator") Abbreviation mitiCreator, @RequestBody Miti miti) throws MitiNotFoundException {
        mitiService.updateMitiByDateMitiCreator(date, mitiCreator, miti);
        LOGGER.info("RESTful call 'PUT miti'");
    }

    @DeleteMapping(value = "/miti/{date}/{mitiCreator}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteMitiByDateMitiCreator(@PathVariable(value = "date") Date date,
        @PathVariable(value = "mitiCreator") Abbreviation mitiCreator) throws MitiNotFoundException {
        mitiService.deleteMitiByDateMitiCreator(date, mitiCreator);
        LOGGER.info("RESTful call 'DELETE miti'");
    }
}
