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
public class PostMitiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testPostMitiProperly() throws Exception {

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

        mvc.perform(post("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.location.value", is("Hannover")));
    }

    @Disabled
    @Test
    void testPostMitiProperlyTwoTimes() throws Exception {

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodySecond))
                .andExpect(status().isOk());

        mvc.perform(get("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].place.location.value", is("Oldenburg")));
    }

    @Test
    void testPostMitiWithEmptyString() throws Exception {

        String jsonBody =
            """
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithoutEntityLocalityAndWithEmptyValues() throws Exception {

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

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithoutEntityLocality() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithEmptyValues() throws Exception {

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

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithEmptyLocalityValue() throws Exception {

        String jsonBody =
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithNullLocalityValue() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"null",
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithLowerCaseValues() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"metzger",
                           "location":"hannover"
                       },
                   "employee":
                       {
                           "firstName":"karl",
                           "lastName":"heinz"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithUpperCaseValues() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"METZGER",
                           "location":"HANNOVER"
                       },
                   "employee":
                       {
                           "firstName":"KARL",
                           "lastName":"HEINZ"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithNumberValues() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"1",
                           "location":"2"
                       },
                   "employee":
                       {
                           "firstName":"3",
                           "lastName":"4"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithSignValues() throws Exception {

        String jsonBody =
            """
                {
                   "place":
                       {
                           "locality":"_",
                           "location":"_"
                       },
                   "employee":
                       {
                           "firstName":"_",
                           "lastName":"_"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithLettersInTimeValue() throws Exception {

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
                   "time":"aa:bb"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMitiWithSignsInTimeValue() throws Exception {

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
                   "time":"__:__"
                },
            """;

        mvc.perform(post("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }
}
