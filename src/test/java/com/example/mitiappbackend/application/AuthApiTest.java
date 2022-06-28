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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to register for the MitiApp")
    @Test
    void testApiRegisterEmployee() throws Exception {

        String jsonBodyRole =
            """
                {
                   "name": "ROLE_USER"
                }
            """;

        String jsonBody =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        mvc.perform(post("/api/auth/role")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRole))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    @DisplayName("An employee wants to login to use the MitiApp")
    @Test
    void testApiLoginEmployee() throws Exception {

        String jsonBodyRole =
            """
                {
                   "name": "ROLE_USER"
                }
            """;

        String jsonBodyRegister =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        String jsonBodyLogin =
            """
                {
                   "username": "TES",
                   "password": "testDummy1#"
                }
            """;

        mvc.perform(post("/api/auth/role")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBodyRole))
                    .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBodyRegister))
                    .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBodyLogin))
                    .andExpect(status().isOk());
    }

    @DisplayName("An employee wants to get feedback when registering with a username that already exists")
    @Test
    void testApiRegisterWithAlreadyUsedUsername() throws Exception {
        String jsonBodyRole =
            """
                {
                   "name": "ROLE_USER"
                }
            """;

        String jsonBodyRegisterOne =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        String jsonBodyRegisterTwo =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        mvc.perform(post("/api/auth/role")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRole))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRegisterOne))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRegisterTwo))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Error: Username is already taken!\"}"));
    }

    @DisplayName("An employee wants to get feedback when registering with an invalid email")
    @Test
    void testApiRegisterInvalidEmail() throws Exception {
        String jsonBodyRole =
            """
                {
                   "name": "ROLE_USER"
                }
            """;

        String jsonBodyRegisterOne =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        String jsonBodyRegisterTwo =
            """
                {
                   "username": "TIS",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        mvc.perform(post("/api/auth/role")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRole))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRegisterOne))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRegisterTwo))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Error: Email is already in use!\"}"));
    }

    @DisplayName("An employee wants to get feedback when logging in to the MitiApp with an invalid username")
    @Test
    void testApiLoginInvalidUsername() throws Exception {
        String jsonBodyRole =
            """
                {
                   "name": "ROLE_USER"
                }
            """;

        String jsonBody =
            """
                {
                   "username": "TES",
                   "password": "testDummy1#"
                }
            """;

        mvc.perform(post("/api/auth/role")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRole))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isUnauthorized());
    }

    /*@DisplayName("An employee wants to register as an admin for the MitiApp")
    @Test
    void testApiRegisterEmployeeWithRoleAdmin() throws Exception {

        String jsonBodyRole =
            """
                {
                   "name": "ROLE_ADMIN"
                }
            """;

        String jsonBody =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_ADMIN"]
                }
            """;

        mvc.perform(post("/api/auth/role")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyRole))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }*/

    @DisplayName("An employee wants to get feedback when registering without a role")
    @Test
    void testApiRegisterWithoutRole() {
        String jsonBodyRegister =
            """
                {
                   "username": "TES",
                   "email": "test@test.de",
                   "password": "testDummy1#",
                   "role": ["ROLE_USER"]
                }
            """;

        NestedServletException thrown = Assertions.assertThrows(NestedServletException.class, () -> {
            mvc.perform(post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBodyRegister))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"message\":\"Error: Role is not found.\"}"));
        });
        Assertions.assertEquals("Request processing failed; nested exception is java.lang.RuntimeException: "
            + "Error: Role is not found.", thrown.getMessage());
    }
}
