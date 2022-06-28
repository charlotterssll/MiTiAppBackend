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
package com.example.mitiappbackend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.domain.user.User;
import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class UserDbTest extends AbstractPersistenceTest {

    @BeforeEach
    public void beforeDbTestInsertUserTestDataIntoDb() {
        entityManager.clear();
    }

    @DisplayName("An employee wants to register for the MitiApp")
    @Test
    public void testDbCreateUser() {
        entityManager.getTransaction().begin();
        User userNew = new User(
            "TES", "test@test.de", "testDummy1#");
        entityManager.getTransaction().commit();

        assertThat(userNew.getUsername()).isEqualTo("TES");
        assertThat(userNew.getEmail()).isEqualTo("test@test.de");
        assertThat(userNew.getPassword()).isEqualTo("testDummy1#");
    }

    @DisplayName("An employee does not want to create an incomplete user with an invalid username")
    @Test
    public void testDbCreateUserIncompleteInvalidUsername() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            User userNew = new User(
                "Test", "test@test.de", "testDummy1#");
            entityManager.getTransaction().commit();
            entityManager.persist(userNew);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Username must only contain capital letters and only three characters",
            thrown.getMessage());
    }

    @DisplayName("An employee does not want to create an incomplete user with an invalid email")
    @Test
    public void testDbCreateUserIncompleteInvalidEmail() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            entityManager.getTransaction().begin();
            User userNew = new User(
                "TES", "testtest.de", "testDummy1#");
            entityManager.getTransaction().commit();
            entityManager.persist(userNew);
            entityManager.getTransaction().commit();
        });
        Assertions.assertEquals(
            "Email must match a valid format",
            thrown.getMessage());
    }
}
