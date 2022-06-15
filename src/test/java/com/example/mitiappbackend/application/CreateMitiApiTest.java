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
import org.springframework.test.web.servlet.MockMvc;

import com.example.mitiappbackend.infrastructure.AbstractPersistenceTest;
import com.example.mitiappbackend.infrastructure.MitiCatchOnSameDayException;

//TODO
//change ID to UUID for persistent db testing
@AutoConfigureMockMvc
@SpringBootTest
public class CreateMitiApiTest extends AbstractPersistenceTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void beforeApiTestClearDb() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @DisplayName("An employee wants to create a lunch table")
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

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Immergrün")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")))
                .andExpect(jsonPath("$.[0].place.street.value", is("Poststraße 1a")))
                .andExpect(jsonPath("$.[0].employee.firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee.lastName.value", is("Kranz")))
                .andExpect(jsonPath("$.[0].employee.abbreviation.value", is("HKR")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("An employee does not want to create an incomplete lunch table")
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
                   "employee":
                       {
                       },
                },
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
                   "employee":
                       {
                           "firstName":"",
                           "lastName":"",
                           "abbreviation":""
                       },
                   "time":"",
                   "date":""
                },
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
                   "employee":
                       {
                           "firstName":"null",
                           "lastName":"null",
                           "abbreviation":"null"
                       },
                   "time":"null",
                   "date":"null"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodyNullValueObjects))
                .andExpect(status().is(400));
    }

    @DisplayName("An employee wants to get feedback when creating a lunch table on a day they already have a lunch table")
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
                       {
                           "firstName":"Hannelore",
                           "lastName":"Kranz",
                           "abbreviation":"HKR"
                       },
                   "time":"12:00",
                   "date":"2022-04-01"
                },
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
        Assertions.assertEquals("This employee already has a lunch table meeting on this day!", thrown.getMessage());
    }
}
