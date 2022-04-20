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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mitiappbackend.application.MiTiNotNestedService;
import com.example.mitiappbackend.application.MiTiService;

@AutoConfigureMockMvc
@SpringBootTest
public class MiTiRequestTest {

    @Autowired
    protected MiTiService miTiService;

    @Autowired
    protected MiTiNotNestedService miTiNotNestedService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetMiTiesResponse() throws Exception {
        mvc.perform(get("/mities")).andExpectAll(
                status().isOk())
                .andDo(print());
    }

    @Test
    void testPostMiTi() throws Exception {

        String jsonBody =
                """
                {"place":{"locality":{"locality":"Schloefe"}},
                "place":{"location":{"location":"Oldenburg"}},
                "employee":{"firstName":{"firstName":"Charlotte"}},
                "employee":{"lastName":{"lastName":"Russell"}},
                "time":"12:00"}
                """;

        mvc.perform(post("/mities/addmiti")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
