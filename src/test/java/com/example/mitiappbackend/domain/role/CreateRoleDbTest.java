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
package com.example.mitiappbackend.domain.role;

import static com.example.mitiappbackend.domain.role.Erole.ROLE_ADMIN;
import static com.example.mitiappbackend.domain.role.Erole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

public class CreateRoleDbTest extends AbstractPersistenceTest {

    @BeforeEach
    public void beforeDbTestInsertRoleTestDataIntoDb() {
        entityManager.clear();
    }

    @DisplayName("An employee wants to create an admin role for a user")
    @Test
    public void testDbCreateAdminRole() {
        entityManager.getTransaction().begin();
        Role role = new Role(ROLE_ADMIN);
        entityManager.getTransaction().commit();

        assertThat(role.getName()).isEqualTo(ROLE_ADMIN);
    }

    @DisplayName("An employee wants to create a user role for a user")
    @Test
    public void testDbCreateUserRole() {
        entityManager.getTransaction().begin();
        Role role = new Role(ROLE_USER);
        entityManager.getTransaction().commit();

        assertThat(role.getName()).isEqualTo(ROLE_USER);
    }
}
