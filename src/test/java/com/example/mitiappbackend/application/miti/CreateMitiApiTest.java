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
import com.example.mitiappbackend.infrastructure.exceptions.MitiCatchOnSameDayException;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "TES", password = "testDummy1#", roles = "USER")
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class CreateMitiApiTest extends AbstractPersistenceTest {

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

    @DisplayName("An employee wants to create a lunch table meeting")
    @Test
    void testApiCreateMiti() throws Exception {

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

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employeeCreator.firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employeeCreator.lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employeeCreator.abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].firstName.value", is("Karl")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].lastName.value", is("Heinz")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].abbreviation.value", is("KHE")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee wants to add a colleague to a lunch table meeting")
    @Test
    void testApiCreateMitiWithColleague() throws Exception {

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

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employeeCreator.firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employeeCreator.lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employeeCreator.abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].firstName.value", is("Karl")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].lastName.value", is("Heinz")))
                .andExpect(jsonPath("$.[0].employeeParticipants[0].abbreviation.value", is("KHE")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee does not want to create an incomplete lunch table meeting")
    @Test
    void testApiCreateMitiIncomplete() throws Exception {

        String jsonBodyEmptyMiti =
            """
            """;

        mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyEmptyMiti))
                .andExpect(status().is(400));

        String jsonBodyMissingValueObjects =
            """
                {
                   "place":
                       {
                       },
                   "employeeCreator":
                       {
                       },
                   "employeeParticipants":
                        [
                           {
                           }
                        ]
                }
            """;

        mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyMissingValueObjects))
                .andExpect(status().is(400));

        String jsonBodyEmptyValueObjects =
            """
                {
                   "place":
                       {
                           "locality":"",
                           "location":"",
                           "street":""
                       },
                   "employeeCreator":
                       {
                           "firstName":"",
                           "lastName":"",
                           "abbreviation":""
                       },
                   "employeeParticipants":
                        [
                           {
                               "firstName":"",
                               "lastName":"",
                               "abbreviation":""
                           }
                        ],
                   "time":"",
                   "date":""
                }
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodyEmptyValueObjects))
                .andExpect(status().is(400));

        String jsonBodyNullValueObjects =
            """
                {
                   "place":
                       {
                           "locality":"null",
                           "location":"null",
                           "street":"null"
                       },
                   "employeeCreator":
                       {
                           "firstName":"null",
                           "lastName":"null",
                           "abbreviation":"null"
                       },
                   "employeeParticipants":
                        [
                           {
                               "firstName":"null",
                               "lastName":"null",
                               "abbreviation":"null"
                           }
                        ],
                   "time":"null",
                   "date":"null"
                }
            """;

        mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodyNullValueObjects))
                .andExpect(status().is(400));
    }

    @DisplayName("An employee wants to get feedback when creating a lunch table meeting on a day they already have a lunch table")
    @Test
    void testApiCreateMitiNotOnSameDay() {
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
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                }
            """;

        String jsonBodySecond =
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
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                }
            """;

        MitiCatchOnSameDayException thrown = Assertions.assertThrows(MitiCatchOnSameDayException.class, () -> {
            mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

            mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBodySecond))
                .andExpect(status().isBadRequest());
        });
        Assertions.assertEquals("This employee already has a lunch table meeting on this day", thrown.getMessage());
    }
}
