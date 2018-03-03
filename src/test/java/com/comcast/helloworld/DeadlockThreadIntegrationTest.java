package com.comcast.helloworld;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeadlockThreadIntegrationTest extends BaseIntegrationTest{


    @Test
    public void simulateThreadDeadLockTest(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/thread/deadlock"),
                HttpMethod.GET, entity, String.class);

        assertThat(jsonPath("$").value(hasItem("success")));
        assertThat(response.getBody()).isEqualToIgnoringCase("success");
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

    }




}
