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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class GetMitiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetMitiProperly() throws Exception {
        mvc.perform(get("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMitiByIdProperly() throws Exception {

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

        mvc.perform(get("/miti/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMitiByFalseIdThrowException() throws Exception {
        NestedServletException thrown = Assertions.assertThrows(NestedServletException.class, () -> {
            mvc.perform(get("/miti/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        });
        Assertions.assertEquals(
            "Request processing failed; nested exception is java.lang.Exception: "
            + "Error in RESTful call 'GET miti by mitiId': 1 does not exist",
            thrown.getMessage());
    }

    @Test
    void testGetMitiWithUpperCaseURL() throws Exception {
        mvc.perform(get("/MITI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void testGetMitiWithIncorrectURLPathBefore() throws Exception {
        mvc.perform(get("/abc/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void testGetMitiWithIncorrectURLPathAfter() throws Exception {
        mvc.perform(get("/miti/abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void testGetMitiWithIncorrectSpelling() throws Exception {
        mvc.perform(get("/mydys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
}
