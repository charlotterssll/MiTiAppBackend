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
public class PostMiTiTest {

    @Autowired
    private MockMvc mvc;

    @Disabled
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
                           "firstName":"John",
                           "lastName":"Doe"
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
                .andExpect(jsonPath("$.[0].place.location.value", is("Oldenburg")));
    }

    @Test
    void testPostMiTiWithEmptyValues() throws Exception {

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
    void testPostEmptyMiTiWithoutLocality() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
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
    void testPostMiTiWithoutLocality() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "location":"Pizzaria"
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
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiBlankLocality() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"",
                           "location":"Doener"
                       },
                   "employee":
                       {
                           "firstName":"Marian",
                           "lastName":"Heck"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiNullLocality() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"null",
                           "location":"Metzger"
                       },
                   "employee":
                       {
                           "firstName":"Karl",
                           "lastName":"Heinz"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }
}
