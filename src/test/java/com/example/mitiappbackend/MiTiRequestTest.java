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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class MiTiRequestTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetMiTies() throws Exception {
        mvc.perform(get("/mities")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testPostMiTi() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"Schloefe",
                           "location":"Oldenburg"
                       },
                   "employee":
                       {
                           "firstName":"Charlotte",
                           "lastName":"Russell"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(get("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].place.locality.value", is("Schloefe")));
    }

    @Test
    void testPostEmptyMiTi() throws Exception {

        String jsonBody =
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

        mvc.perform(post("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testDeleteMiTi() throws Exception {
        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"Schloefe",
                           "location":"Oldenburg"
                       },
                   "employee":
                       {
                           "firstName":"Charlotte",
                           "lastName":"Russell"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(delete("/mities/{miTiId}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Disabled
    @Test
    void testEditMiTi() throws Exception {

        String jsonBody =
            """
                {"place":
                       {"locality":"Schloefe",
                           "location":"Oldenburg"},
               "employee":
                   {"firstName":"Charlotte",
                       "lastName":"Russell"},
               "time":"12:00"},
            """;

        mvc.perform(post("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk());

        mvc.perform(put("/mities/{miTiId}", 1)
            .content("""
                {"place":
                       {"locality":"Schloefe",
                           "location":"Oldenburg"},
               "employee":
                    {"firstName":"Marian,
                       "lastName":"Heck"},
               "time":"12:00"},
            """)

            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.[0].employee.firstName.value", is("Marian")))
            .andExpect(jsonPath("$.[0].employee.lastName.value", is("Heck")));

        mvc.perform(get("/mities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
