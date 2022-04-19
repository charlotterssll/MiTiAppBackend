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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.example.mitiappbackend.application.MiTiNotNestedService;
import com.example.mitiappbackend.application.MiTiService;
import com.example.mitiappbackend.domain.entities.MiTi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class MiTiIntegrationTest {

    @Autowired
    protected MiTiService miTiService;

    @Autowired
    protected MiTiNotNestedService miTiNotNestedService;

    @Autowired
    private MockMvc mvc;

    List<MiTi> mitiesList = new ArrayList<>();

    @Test
    void testGetMiTies() throws Exception {
        mvc.perform(get("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(mitiesList)))
                .andExpect(status().isOk());
        assertTrue(miTiService.getMiTies().isEmpty());
    }

    @Test
    void testGetMiTiesNotNested() throws Exception {
        mvc.perform(get("/mities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(mitiesList)))
                .andExpect(status().isOk());
        assertTrue(miTiNotNestedService.getMiTiesNotNested().isEmpty());
    }
}
