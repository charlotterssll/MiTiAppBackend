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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.miti.Miti;
import com.example.mitiappbackend.domain.miti.MitiService;
import com.example.mitiappbackend.infrastructure.MitiNotFoundException;

@RestController
@CrossOrigin
public class MitiResource {

    private final Logger logger = Logger.getLogger(MitiResource.class.getSimpleName());

    @Autowired
    private MitiService mitiService;

    //TODO
    //Überprüfung der ID-Generierung und Hochzählung nach Migration zur persistenten DB
    @PostMapping(value = "/miti", consumes = "application/json")
    public void createMiti(@RequestBody Miti miti) {
        logger.info("RESTful call 'POST miti'");
        mitiService.createMiti(miti);
    }

    @GetMapping(value = "/miti", produces = "application/json")
    public List<Miti> readMiti() {
        logger.info("RESTful call 'GET miti'");
        return mitiService.readMiti();
    }

    @GetMapping(value = "/miti/{mitiId}", produces = "application/json")
    public Miti readMitiByUuid(@PathVariable Long mitiId) throws MitiNotFoundException {
        Miti mitiRead = mitiService.readMitiByUuid(mitiId);

        if (mitiRead == null) {
            throw new MitiNotFoundException(mitiId);
        }

        logger.info("RESTful call 'GET miti by mitiId'");
        return mitiService.readMitiByUuid(mitiId);
    }

    @PutMapping(value = "/miti/{mitiId}")
    public void updateMitiByUuid(@PathVariable(value = "mitiId") Long mitiId, @RequestBody Miti miti) throws MitiNotFoundException {
        Miti mitiUpdate = mitiService.readMitiByUuid(mitiId);

        if (mitiUpdate == null) {
            throw new MitiNotFoundException(mitiId);
        }

        logger.info("RESTful call 'PUT miti'");
        mitiService.updateMitiByUuid(mitiId, miti);
    }

    @DeleteMapping(value = "/miti/{mitiId}")
    public void deleteMitiByUuid(@PathVariable Long mitiId) throws MitiNotFoundException {
        Miti mitiDelete = mitiService.readMitiByUuid(mitiId);

        if (mitiDelete == null) {
            throw new MitiNotFoundException(mitiId);
        }

        logger.info("RESTful call 'DELETE miti'");
        mitiService.deleteMitiByUuid(mitiId);
    }
}
