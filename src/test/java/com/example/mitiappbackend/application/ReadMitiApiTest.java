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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.example.mitiappbackend.infrastructure.MitiNotFoundException;

//TODO
//change ID to UUID for persistent db testing
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class ReadMitiApiTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("Employee wants to read information about already existing lunch tables")
    @Test
    void testReadMiti() throws Exception {
        mvc.perform(get("/miti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testReadMitiByFalseIdThrowException() throws MitiNotFoundException {
        Long mitiId = 1L;
        NestedServletException thrown = Assertions.assertThrows(NestedServletException.class, () -> {
            mvc.perform(get("/miti/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        });
        Assertions.assertEquals(
            "Request processing failed; nested exception is com.example.mitiappbackend.infrastructure.MitiNotFoundException: "
            + "Miti with mitiId: " + mitiId + " could not be found",
            thrown.getMessage());
    }

    /*@Test
    void testReadMitiByFalseIdThrowExceptionTestTwo() throws Exception {
        Long mitiId = 1L;
        mvc.perform(get("/miti/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MitiNotFoundException))
                .andExpect(result -> assertEquals("Request processing failed; nested exception is " +
                        "com.example.mitiappbackend.infrastructure.MitiNotFoundException: "
                        + "Miti with mitiId: " + mitiId + " could not be found", result.getResolvedException().getMessage()));
    }*/
}
