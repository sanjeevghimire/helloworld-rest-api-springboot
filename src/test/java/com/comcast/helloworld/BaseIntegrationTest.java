package com.comcast.helloworld;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.AsyncRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class BaseIntegrationTest {

    protected Logger logger = LoggerFactory.getLogger(UserControllerIntegrationTest.class);

    @LocalServerPort
    protected int port;


    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected AsyncRestTemplate asyncRestTemplate;

    protected HttpHeaders headers = new HttpHeaders();

    protected  String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    /**
     * Empty test for base class. Coz without this it would throw error <p> no runnable methods
     */
    @Test
    public void testEmpty(){
        assertThat(true).isEqualTo(true);
    }



}
