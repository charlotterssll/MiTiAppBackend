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
package com.example.mitiappbackend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractPersistenceTest {
    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    @BeforeAll
    public static void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("miti");
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void closeEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            return;
        }
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        clearDatabase();
    }

    private void clearDatabase() {
        EntityManager entityMiTinger = entityManagerFactory.createEntityManager();
        try {
            entityMiTinger.getTransaction().begin();
            entityMiTinger.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
            for (Object table : entityMiTinger.createNativeQuery("SHOW TABLES").getResultList()) {
                entityMiTinger.createNativeQuery("TRUNCATE TABLE " + ((Object[])table)[0]).executeUpdate();
            }
            entityMiTinger.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
            entityMiTinger.getTransaction().commit();
        } finally {
            if (entityMiTinger.getTransaction().isActive()) {
                entityMiTinger.getTransaction().rollback();
            }
            if (entityMiTinger.isOpen()) {
                entityMiTinger.close();
            }
        }
    }
}
