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
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.miti.Miti;
import com.example.mitiappbackend.domain.miti.MitiRepository;
import com.example.mitiappbackend.domain.miti.MitiService;

@RestController
@CrossOrigin
public class MitiResource {

    private final Logger logger = Logger.getLogger(MitiResource.class.getSimpleName());

    @Autowired
    private MitiService mitiService;

    @Autowired
    private MitiRepository mitiRepository;

    @GetMapping(value = "/miti", produces = "application/json")
    public List<Miti> getMiti() throws Exception {
        try {
            logger.info("RESTful call 'GET miti'");
            return mitiService.getMiti();
        } catch (Exception e) {
            logger.info("Error in RESTful call 'GET miti'");
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/miti/{mitiId}", produces = "application/json")
    public Optional<Miti> getMitiByMitiId(@PathVariable Long mitiId) throws Exception {
        try {
            logger.info("RESTful call 'GET miti by mitiId'");
            return mitiService.getMitiByMitiId(mitiId);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'GET miti by mitiId'");
            throw new Exception(e);
        }
    }

    @PostMapping(value = "/miti", consumes = "application/json")
    public void createMiti(@RequestBody Miti miti) throws Exception {
        try {
            logger.info("RESTful call 'POST miti'");
            mitiService.createMiti(miti);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'POST miti'");
            throw new Exception(e);
        }
    }

    @PutMapping(value = "/miti/{mitiId}")
    public void editMiti(@PathVariable(value = "mitiId") Long mitiId, @RequestBody Miti miti) throws Exception {
        try {
            logger.info("RESTful call 'PUT miti'");
            Miti mitiToEdit = mitiRepository
                .findById(mitiId)
                .orElseThrow(() -> new Exception("Miti not found on : " + mitiId));
            mitiToEdit.setPlace(miti.getPlace());
            mitiToEdit.setEmployee(miti.getEmployee());
            mitiToEdit.setTime(miti.getTime());
            mitiService.createMiti(mitiToEdit);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'PUT miti'");
            throw new Exception(e);
        }
    }

    @DeleteMapping(value = "/miti/{mitiId}")
    public void deleteMiti(@PathVariable Long mitiId) throws Exception {
        try {
            logger.info("RESTful call 'DELETE miti'");
            mitiService.deleteMiti(mitiId);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'DELETE miti'");
            throw new Exception(e);
        }
    }
}
