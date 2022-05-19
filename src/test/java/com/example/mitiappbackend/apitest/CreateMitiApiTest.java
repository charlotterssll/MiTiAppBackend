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
package com.example.mitiappbackend.apitest;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

//TODO
//change ID to UUID for persistent db testing
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class CreateMitiApiTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("Employee wants to create a lunch table")
    @Test
    void testCreateMiti() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"Metzger",
                           "location":"Hannover"
                       },
                   "employee":
                       {
                           "firstName":"Karl",
                           "lastName":"Heinz"
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
                .andExpect(jsonPath("$.[0].place.locality.value", is("Metzger")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Hannover")))
                .andExpect(jsonPath("$.[0].employee.firstName.value", is("Karl")))
                .andExpect(jsonPath("$.[0].employee.lastName.value", is("Heinz")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")))
                .andExpect(jsonPath("$.[0].date.value", is("2022-04-01")));
    }

    @DisplayName("Employee does not want to create an incomplete lunch table")
    @Test
    void testCreateMitiIncomplete() throws Exception {

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
                           "location":""
                       },
                   "employee":
                       {
                           "firstName":"",
                           "lastName":""
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
                           "location":"null"
                       },
                   "employee":
                       {
                           "firstName":"null",
                           "lastName":"null"
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
}
