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
package com.example.mitiappbackend.application.employee;

import static org.hamcrest.core.Is.is;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeAlreadyExists;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "TES", password = "testDummy1#", roles = "USER")
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class CreateEmployeeApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to create an employee")
    @Test
    void testApiCreateEmployee() throws Exception {

        String jsonBody =
            """
                {
                   "firstName":"Hannelore",
                   "lastName":"Kranz",
                   "abbreviation":"HKR"
                },
            """;

        mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(get("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].abbreviation.value", is("HKR")));
    }

    @DisplayName("An employee does not want to create an incomplete employee")
    @Test
    void testApiCreateEmployeeIncomplete() throws Exception {

        String jsonBodyEmptyPlace =
            """
            """;

        mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyEmptyPlace))
                .andExpect(status().is(400));

        String jsonBodyMissingValueObjects =
            """
                {
                },
            """;

        mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyMissingValueObjects))
                .andExpect(status().is(400));

        String jsonBodyEmptyValueObjects =
            """
                {
                   "firstName":"",
                   "lastName":"",
                   "abbreviation":""
                },
            """;

        mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyEmptyValueObjects))
                .andExpect(status().is(400));

        String jsonBodyNullValueObjects =
            """
                {
                   "firstName":"null",
                   "lastName":"null",
                   "abbreviation":"null"
                },
            """;

        mvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyNullValueObjects))
                .andExpect(status().is(400));
    }

    @DisplayName("An employee wants to get feedback when creating an employee who already exists")
    @Test
    void testApiCreateEmployeeWhoAlreadyExists() {
        String jsonBody =
            """
               {
                   "firstName":"Hannelore",
                   "lastName":"Kranz",
                   "abbreviation":"HKR"
               }
            """;

        String jsonBodySecond =
            """
               {
                   "firstName":"Hannelore",
                   "lastName":"Kranz",
                   "abbreviation":"HKR"
               }
            """;

        EmployeeAlreadyExists thrown = Assertions.assertThrows(EmployeeAlreadyExists.class, () -> {
            mvc.perform(post("/employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
                    .andExpect(status().isOk());

            mvc.perform(post("/employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonBodySecond))
                    .andExpect(status().isBadRequest());
        });
        Assertions.assertEquals("This employee already exists.", thrown.getMessage());
    }
}
