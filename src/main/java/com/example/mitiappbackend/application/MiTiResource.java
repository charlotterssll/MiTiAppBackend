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

import com.example.mitiappbackend.domain.miti.MiTi;
import com.example.mitiappbackend.domain.miti.MiTiService;

@RestController
@CrossOrigin
public class MiTiResource {

    private final Logger logger = Logger.getLogger(MiTiResource.class.getSimpleName());

    @Autowired
    private MiTiService miTiService;

    @GetMapping(value = "/mities", produces = "application/json")
    public List<MiTi> getMiTis() throws Exception {
        try {
            logger.info("RESTful call 'GET mities'");
            return miTiService.getMiTies();
        } catch (Exception e) {
            logger.info("Error in RESTful call 'GET mities'");
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/mities/{miTiId}", produces = "application/json")
    public Optional<MiTi> getMiTiByMiTiId(@PathVariable Long miTiId) throws Exception {
        try {
            logger.info("RESTful call 'GET miti by mitiid'");
            return miTiService.getMiTiByMiTiId(miTiId);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'GET miti by mitiid'");
            throw new Exception(e);
        }
    }

    @PostMapping(value = "/mities", consumes = "application/json")
    public void createMiTi(@RequestBody MiTi miTi) throws Exception {
        try {
            logger.info("RESTful call 'POST miti'");
            miTiService.createMiTi(miTi);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'POST miti'");
            throw new Exception(e);
        }
    }

    @DeleteMapping(value = "/mities/{miTiId}")
    public void deleteMiTi(@PathVariable Long miTiId) throws Exception {
        try {
            logger.info("RESTful call 'DELETE miti'");
            miTiService.deleteMiTi(miTiId);
        } catch (Exception e) {
            logger.info("Error in RESTful call 'DELETE miti'");
            throw new Exception(e);
        }
    }

    @PutMapping(value = "/mities/{miTiId}")
    public void editMiTi(@RequestBody MiTi miTi, @PathVariable Long miTiId) throws Exception {
        try {
            logger.info("RESTful call 'PUT miti'");
            /*MiTi miTiToEdit = miTiService.findByMiTiId(miTiId);
            miTiToEdit.setPlace(miTi.getPlace().getLocality().getValue());
            miTiToEdit.setPlace(miTi.getPlace().getLocation().getValue());
            miTiToEdit.setEmployee(miTi.getEmployee().getFirstName().getValue());
            miTiToEdit.setEmployee(miTi.getEmployee().getLastName().getValue());
            miTiToEdit.setTime(miTi.getTime());
            miTiService.createMiTi(miTiToEdit);*/
        } catch (Exception e) {
            logger.info("Error in RESTful call 'PUT miti'");
            throw new Exception(e);
        }
    }
}
