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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;
import com.example.mitiappbackend.infrastructure.MitiNotFoundException;

//TODO
//change ID to UUID for persistent db testing
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "testdummy", password = "testdummy", roles = "USER")
public class DeleteMitiApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to delete a lunch table")
    @Test
    void testApiDeleteMiti() throws Exception {
        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"Immergrün",
                           "location":"Oldenburg",
                           "street":"Poststraße 1a"
                       },
                   "employee":
                       {
                           "firstName":"Hannelore",
                           "lastName":"Kranz",
                           "abbreviation":"HKR"
                       },
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(delete("/miti/{mitiId}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("An employee wants to get an error message when trying to delete a nonexistent lunch table via URL")
    @Test
    void testApiDeleteMitiByFalseIdThrowException() {
        Long mitiId = 1L;
        MitiNotFoundException thrown = Assertions.assertThrows(MitiNotFoundException.class, () -> {
            mvc.perform(delete("/miti/{mitiId}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        });
        Assertions.assertEquals("Miti with mitiId: " + mitiId + " could not be found", thrown.getMessage());
    }
}
