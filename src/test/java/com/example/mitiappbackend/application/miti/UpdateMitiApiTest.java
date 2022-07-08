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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "TES", password = "testDummy1#", roles = "USER")
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class UpdateMitiApiTest extends AbstractPersistenceTest {

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
                    "email": "karlheinz@web.de",
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

    @DisplayName("An employee wants to update all except employee information on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectsAll() throws Exception {

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
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"Metzger",
                            "location":"Essen",
                            "street":"Buchstraße 50d"
                        },
                   "employee":
                        [
                            {
                                "firstName":"Hannelore",
                                "lastName":"Kranz",
                                "abbreviation":"HKR"
                            }
                        ],
                   "time":"14:30",
                   "date":"2022-05-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Metzger")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Essen")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Buchstraße 50d")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("14:30")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-05-01")));
    }

    @DisplayName("An employee wants to update the locality on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectLocality() throws Exception {

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
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                       ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"Metzger",
                            "location":"Oldenburg",
                            "street":"Poststraße 1a"
                        },
                   "employee":
                        [
                            {
                                "firstName":"Hannelore",
                                "lastName":"Kranz",
                                "abbreviation":"HKR"
                            }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Metzger")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee wants to update the location on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectLocation() throws Exception {

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
                        [
                            {
                                "firstName":"Hannelore",
                                "lastName":"Kranz",
                                "abbreviation":"HKR"
                            }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"Immergrün",
                            "location":"Essen",
                            "street":"Poststraße 1a"
                        },
                   "employee":
                        [
                            {
                                "firstName":"Hannelore",
                                "lastName":"Kranz",
                                "abbreviation":"HKR"
                            }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Essen")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee wants to update the street with house number on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectStreet() throws Exception {

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
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"Immergrün",
                            "location":"Essen",
                            "street":"Buchstraße 50d"
                        },
                   "employee":
                        [
                            {
                                "firstName":"Hannelore",
                                "lastName":"Kranz",
                                "abbreviation":"HKR"
                            }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Essen")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Buchstraße 50d")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee wants to update the time on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectTime() throws Exception {

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
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
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
                   "employee":
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"14:30",
                   "date":"2022-04-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("14:30")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee wants to update the date on an existing lunch table meeting")
    @Test
    void testApiUpdateMitiValueObjectDate() throws Exception {

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
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-04-01"
                },
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
                   "employee":
                        [
                           {
                               "firstName":"Hannelore",
                               "lastName":"Kranz",
                               "abbreviation":"HKR"
                           }
                        ],
                   "time":"12:00",
                   "date":"2022-05-01"
                },
            """;

        mvc.perform(post("/miti")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/miti/{mitiId}", 1)
                .content(jsonBodySecond)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employee[0].firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee[0].lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee[0].abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-05-01")));
    }
}
