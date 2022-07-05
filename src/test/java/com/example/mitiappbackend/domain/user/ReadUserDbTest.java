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
package com.example.mitiappbackend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class ReadUserDbTest extends AbstractPersistenceTest {

    private User user;

    @BeforeEach
    public void beforeDbTestInsertUserTestDataIntoDb() {
        entityManager.getTransaction().begin();
        user = new User("TES", "test@test.de", "testDummy1#");
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to read information about an already existing users")
    @Test
    public void testDbReadUser() {
        assertThat(user.getUsername()).isEqualTo("TES");
        assertThat(user.getEmail()).isEqualTo("test@test.de");
        assertThat(user.getPassword()).isEqualTo("testDummy1#");
    }
}
