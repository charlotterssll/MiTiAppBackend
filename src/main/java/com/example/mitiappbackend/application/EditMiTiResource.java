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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.miti.MiTi;
import com.example.mitiappbackend.domain.miti.MiTiService;

@RestController
@CrossOrigin
public class EditMiTiResource {

    private final Logger logger = Logger.getLogger(PostMiTiResource.class.getSimpleName());

    @Autowired
    private MiTiService miTiService;

    @PutMapping(value = "/mities/{miTiId}")
    public void editMiTi(@RequestBody MiTi miTi, @PathVariable Long miTiId) {
        logger.info("RESTful call 'EDIT miti'");
        /*MiTi miTiToEdit = miTiService.findByMiTiId(miTiId);
        miTiToEdit.setPlace(miTi.getPlace().getLocality().getValue());
        miTiToEdit.setPlace(miTi.getPlace().getLocation().getValue());
        miTiToEdit.setEmployee(miTi.getEmployee().getFirstName().getValue());
        miTiToEdit.setEmployee(miTi.getEmployee().getLastName().getValue());
        miTiToEdit.setTime(miTi.getTime());
        miTiService.createMiTi(miTiToEdit);*/
    }
}
