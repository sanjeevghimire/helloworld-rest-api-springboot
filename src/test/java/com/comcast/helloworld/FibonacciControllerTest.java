package com.comcast.helloworld;


import com.comcast.helloworld.controller.FibonacciController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FibonacciController.class)
public class FibonacciControllerTest {

    private static final String FIVE = "5";
    @Autowired
    private MockMvc restUserMockMvc;

    @MockBean
    RestTemplateBuilder restTemplateBuilder;


    @Test
    public void getFibonacciNumberByRecursion() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/fibonacci/r/5")
                .accept(MediaType.APPLICATION_JSON);

        restUserMockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(FIVE));
    }


    @Test
    public void getFibonacciNumberByDP() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/fibonacci/d/5")
                .accept(MediaType.APPLICATION_JSON);

        restUserMockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(FIVE));
    }






}
