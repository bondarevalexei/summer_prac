package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest()
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGenerateErrorInternalServerError() throws Exception {
        MvcResult result = mockMvc.perform(get("/generate-error"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal Server Error"))
                .andReturn();

        int status = result.getResponse().getStatus();
        System.out.println("HTTP Status: " + status);
    }
}