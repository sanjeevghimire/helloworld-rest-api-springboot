package com.comcast.helloworld;


import com.comcast.helloworld.dto.UserDTO;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class UserControllerIntegrationTest extends BaseIntegrationTest {




    @Test
    public void testGetAllComcastUsers() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.GET, entity, String.class);

        assertThat(jsonPath("$.[0].username").value(hasItem("sanjeevghimire")));
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());


    }


    @Test
    public void testCreateComcastUsers() throws JSONException {

        UserDTO user = new UserDTO();
        user.setFirstName("S");
        user.setLastName("G");
        user.setEmail("sg@sg.com");
        user.setUsername("sg");

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserDTO> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.POST, entity, String.class);
        headers = response.getHeaders();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals("A comcast user is created with identifier sg",headers.get("X-helloWorld-alert").get(0));
        assertThat(jsonPath("$.[0].id").value(new GreaterThan<>(0)));


    }


    /**
     * Successfull deletion of comcast user.
     * @throws Exception
     */
    @Test
    public void deleteComcastUserTest() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/users/1"),
                HttpMethod.DELETE, entity, String.class);
        headers = response.getHeaders();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("A user is deleted with identifier 1",headers.get("X-helloWorld-alert").get(0));

    }

    /**
     * Get all comcast user test
     * @throws Exception
     */
    @Test
    public void getAllUserPosts() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        Future<ResponseEntity<String>> futureResponse = asyncRestTemplate.exchange(
                createURLWithPort("/api/users/posts"),
                HttpMethod.GET, entity, String.class);


        ResponseEntity<String> response = null;
        try {
            response = futureResponse.get();
            logger.info("Response received");
            logger.info(response.getBody());

        } catch (InterruptedException | ExecutionException e) {

        }



        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(jsonPath("$").isArray());



    }



}
