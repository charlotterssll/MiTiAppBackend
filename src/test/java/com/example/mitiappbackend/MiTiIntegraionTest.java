package com.example.mitiappbackend;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.mitiappbackend.application.MiTiResource;
import com.example.mitiappbackend.domain.entities.MiTi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MiTiResource.class)
public class MiTiIntegraionTest {
/*
    List<MiTi> listi = new ArrayList<>();

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetMiTi(){
        RequestBuilder request = get("/mities");
        assertFalse(listi.isEmpty());
    }*/
}
