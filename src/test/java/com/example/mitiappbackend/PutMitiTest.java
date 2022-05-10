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
package com.example.mitiappbackend;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class PutMitiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testEditMitiProperly() throws Exception {

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
                   "time":"12:00"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"Immergrün",
                            "location":"Oldenburg"
                        },
                    "employee":
                        {
                            "firstName":"Hannelore",
                            "lastName":"Kranz"
                        },
                    "time":"14:30"
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
                .andExpect(jsonPath("$.[0].employee.firstName.value", is("Hannelore")))
                .andExpect(jsonPath("$.[0].employee.lastName.value", is("Kranz")));
    }

    @Test
    void testEditMitiEmptyValueDoesNotOverwriteOriginalValue() throws Exception {

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
                   "time":"12:00"
                },
            """;

        String jsonBodySecond =
            """
                {
                   "place":
                       {
                            "locality":"",
                            "location":"Hannover"
                        },
                    "employee":
                        {
                            "firstName":"Karl",
                            "lastName":"Heinz"
                        },
                    "time":"12:00"
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
                .andExpect(status().is(400));

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].place.locality.value", is("Metzger")));
    }

    @Test
    void testEditMitiEmptyValuesDontOverwriteOriginalValues() throws Exception {

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
                   "time":"12:00"
                },
            """;

        String jsonBodySecond =
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
                    "time":""
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
                .andExpect(status().is(400));

        mvc.perform(get("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.locality.value", is("Metzger")))
                .andExpect(jsonPath("$.[0].place.location.value", is("Hannover")))
                .andExpect(jsonPath("$.[0].employee.firstName.value", is("Karl")))
                .andExpect(jsonPath("$.[0].employee.lastName.value", is("Heinz")))
                .andExpect(jsonPath("$.[0].time.value", is("12:00")));
    }
}
