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
package com.example.mitiappbackend.application.miti;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mitiappbackend.domain.employee.Abbreviation;
import com.example.mitiappbackend.domain.miti.Date;
import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;
import com.example.mitiappbackend.infrastructure.exceptions.MitiNotFoundException;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "TES", password = "testDummy1#", roles = "USER")
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class DeleteMitiApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() throws Exception {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        String jsonBodyRole =
            """
                {
                   "name": "ROLE_ADMIN"
                }
            """;

        String jsonBodySignUp =
            """
                {
                    "username":"HKR",
                    "email": "hannelore@gmail.com",
                    "password":"Testtest1#",
                    "role": ["ROLE_ADMIN"]
                }
            """;

        String jsonBodySignUpTwo =
            """
                {
                    "username":"KHE",
                    "email": "karlheinz@gmail.com",
                    "password":"Hallohallo1#",
                    "role": ["ROLE_ADMIN"]
                }
            """;

        String jsonBodySignIn =
            """
                {
                    "username":"HKR",
                    "password":"Testtest1#"
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
                .content(jsonBodySignUp))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodySignUpTwo))
                .andExpect(status().isOk());

        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodySignIn))
                .andExpect(status().isOk());
    }

    @DisplayName("An employee wants to delete a lunch table meeting")
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
                   "employeeCreator":
                       {
                           "firstName":"Hannelore",
                           "lastName":"Kranz",
                           "abbreviation":"HKR"
                       },
                   "employeeParticipants":
                        [
                           {
                               "firstName":"Karl",
                               "lastName":"Heinz",
                               "abbreviation":"KHE"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                }
            """;

        mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(delete("/miti/2022-04-01/HKR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("An employee wants to get an error message when trying to delete a nonexistent lunch table meeting via URL")
    @Test
    void testApiDeleteMitiByFalseIdThrowException() {
        String quotationMarks = "'";
        Date date = new Date("2022-04-01");
        Abbreviation mitiCreator = new Abbreviation("HKR");
        MitiNotFoundException thrown = Assertions.assertThrows(MitiNotFoundException.class, () -> {
            mvc.perform(delete("/miti/2022-04-01/HKR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        });
        Assertions.assertEquals("Miti with this date " + quotationMarks + date + quotationMarks
            + " of " + mitiCreator + " could not be found", thrown.getMessage());
    }
}
