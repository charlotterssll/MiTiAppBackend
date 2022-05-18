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
package com.example.mitiappbackend.domain.miti;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitiService {

    @Autowired
    private MitiRepository mitiRepository;

    @Transactional
    public void createMiti(Miti miti) {
        mitiRepository.createMiti(miti);
    }

    @Transactional
    public List<Miti> readMiti() {
        return mitiRepository.readMiti();
    }

    @Transactional
    public Miti readMitiByUuid(Long mitiId) {
        return mitiRepository.readMitiByUuid(mitiId);
    }

    @Transactional
    public void updateMitiByUuid(Long mitiId, Miti miti) {
        mitiRepository.updateMitiByUuid(mitiId, miti);
    }

    @Transactional
    public void deleteMitiByUuid(Long mitiId) {
        mitiRepository.deleteMitiByUuid(mitiId);
    }
}
