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

    @Test
    void testPostMiTiProperly() throws Exception {

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

        mvc.perform(post("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(get("/mities")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].place.location.value", is("Hannover")));
    }

    @Test
    void testPostMiTiProperlyTwoTimes() throws Exception {

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
                           "locality":"Immer Grün",
                           "location":"Oldenburg"
                       },
                   "employee":
                       {
                           "firstName":"Hannelore",
                           "lastName":"Kranz"
                       },
                   "time":"12:00"
                },
            """;

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodySecond))
                .andExpect(status().isOk());

        mvc.perform(get("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].place.location.value", is("Oldenburg")));
    }

    @Test
    void testPostMiTiWithEmptyString() throws Exception {

        String jsonBody =
            """
            """;

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithoutEntityLocalityAndWithEmptyValues() throws Exception {

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
    void testPostMiTiWithoutEntityLocality() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
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
    void testPostMiTiWithEmptyLocalityValue() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithNullLocalityValue() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithLowerCaseValues() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithUpperCaseValues() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithNumberValues() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithSignValues() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithLettersInTimeValue() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }

    @Test
    void testPostMiTiWithSignsInTimeValue() throws Exception {

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

        mvc.perform(post("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is(400));
    }
}
