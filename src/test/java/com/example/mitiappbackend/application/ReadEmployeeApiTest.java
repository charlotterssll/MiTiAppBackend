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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.example.mitiappbackend.infrastructure.EmployeeNotFoundException;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "TES", password = "testDummy1#", roles = "USER")
public class ReadEmployeeApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to read information about already existing employees")
    @Test
    void testApiReadEmployee() throws Exception {
        mvc.perform(get("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("An employee wants to read information about one already existing employee")
    @Test
    void testApiReadEmployeeById() throws Exception {
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

        mvc.perform(get("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("An employee wants to get an error message when trying to read a nonexistent employee via URL")
    @Test
    void testApiReadEmployeeByFalseIdThrowException() {
        Long employeeId = 1L;
        EmployeeNotFoundException thrown = Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            mvc.perform(get("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        });
        Assertions.assertEquals("Employee with employeeId: " + employeeId + " could not be found", thrown.getMessage());
    }
}
