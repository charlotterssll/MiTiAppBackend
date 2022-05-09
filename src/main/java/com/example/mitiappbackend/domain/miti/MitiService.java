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
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitiService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MitiRepository mitiRepository;

    public List<Miti> getMiti() {
        return entityManager.createQuery("SELECT M FROM Miti M", Miti.class).getResultList();
    }

    /*@Transactional
    public Optional<Miti> findByMitiId(Long mitiId) {
        return entityManager.createNamedQuery(Miti.FIND_BY_NUMBER, Miti.class)
                .setParameter("number", mitiId.toString())
                .getResultList().stream().findAny();
    }*/

    @Transactional
    public Optional<Miti> getMitiByMitiId(Long mitiId) {
        return mitiRepository.findById(mitiId);
    }

    @Transactional
    public void createMiti(Miti miti) {
        entityManager.persist(miti);
    }

    @Transactional
    public void deleteMiti(Long mitiId) {
        Miti miti = entityManager.find(Miti.class, mitiId);
        entityManager.remove(miti);
    }

    @Transactional
    public void editMiti(Miti miti, Long mitiId) {
        Optional<Miti> mitiToEdit = mitiRepository.findById(mitiId);
    }
}
