package com.comcast.helloworld;


import com.comcast.helloworld.controller.DeadlockThreadController;
import com.comcast.helloworld.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(DeadlockThreadController.class)
public class DeadlockThreadControllerTest {


    private static final String SUCCESS = "success";

    @Autowired
    private MockMvc restUserMockMvc;

    @MockBean
    RestTemplateBuilder restTemplateBuilder;

    @Test
    public void testDeadLockRestApiReturnSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/thread/deadlock")
                .accept(MediaType.APPLICATION_JSON);

        restUserMockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(SUCCESS));



    }


}
