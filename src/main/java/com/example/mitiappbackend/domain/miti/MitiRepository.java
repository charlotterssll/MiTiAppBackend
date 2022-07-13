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
import org.springframework.stereotype.Repository;

import com.example.mitiappbackend.domain.employee.Abbreviation;

@Repository
public class MitiRepository {

    @Autowired
    private EntityManager entityManager;

    public void createMiti(Miti miti) {
        entityManager.persist(miti);
    }

    @Transactional
    public List<Miti> readMiti() {
        return entityManager.createNamedQuery(Miti.READ_ALL, Miti.class).getResultList();
    }

    @Transactional
    public Optional<Miti> readMitiById(Date date, Abbreviation employeeCreator) {
        return entityManager.createNamedQuery(Miti.READ_BY_DATE_EMPLOYEE_CREATOR, Miti.class)
                .setParameter("date", date)
                .setParameter("employeeCreator", employeeCreator)
                .getResultList().stream().findAny();
    }

    @Transactional
    public void deleteMitiById(Date date, Abbreviation employeeCreator) {
        Optional<Miti> mitiToDelete = this.readMitiById(date, employeeCreator);
        if (mitiToDelete.isPresent()) {
            entityManager.remove(mitiToDelete.get());
        }
    }
}
