package com.comcast.helloworld;


import com.comcast.helloworld.controller.UserController;
import com.comcast.helloworld.dto.UserDTO;
import com.comcast.helloworld.dto.UserPost;
import com.comcast.helloworld.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {


    private static final String DEFAULT_USERNAME = "sanjeevghimire";
    private static final String DEFAULT_EMAIL = "gsanjeev7@gmail.com";
    private static final String DEFAULT_FIRSTNAME = "sanjeev";
    private static final String DEFAULT_LASTNAME = "ghimire";

    private static final int DEFAULT_ID = 1;
    private static final int DEFAULT_USERID = 1;
    private static final String DEFAULT_TITLE = "Test Title";
    private static final String DEFAULT_BODY = "Test body";

    private  UserDTO user1 = null;

    List<UserDTO> mockUsers = null;

    List<UserPost> mockPosts = null;

    private static final String userJSON = "{\"firstName\":\"testFName\",\"lastName\":\"testLName\",\"email\":\"email@test.com\",\"username\":\"testusername\"}";



    @Autowired
    private MockMvc restUserMockMvc;

    @MockBean
    private UserService userService;


    @MockBean
    RestTemplateBuilder restTemplateBuilder;


    @Before
    public void setup() {

        user1 = new UserDTO();
        user1.setId(10L);
        user1.setFirstName("sanjeev");
        user1.setLastName("ghimire");
        user1.setEmail("gsanjeev7@gmail.com");
        user1.setUsername("sanjeevghimire");

        mockUsers = Arrays.asList(user1);

        UserPost userPost = new UserPost();
        userPost.setId(1);
        userPost.setUserId(1);
        userPost.setTitle("Test Title");
        userPost.setBody("Test body");

        mockPosts = Arrays.asList(userPost);

    }


    /**
     * Get all comcast user test
     * @throws Exception
     */
    @Test
    public void getAllComcastUsers() throws Exception {
        Mockito.when(
                userService.getAllComcastUsers()).thenReturn(mockUsers);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users")
                .accept(MediaType.APPLICATION_JSON);

                restUserMockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

    }

    /**
     * Successfull creation of comcast user test
     * @throws Exception
     */
    @Test
    public void createComcastUserTest() throws Exception {

        Mockito.when(
                userService.createComcastUser(Mockito.any(UserDTO.class))).thenReturn(user1);
        Mockito.when(
                userService.findUserByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(
                userService.findUserByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users")
                .accept(MediaType.APPLICATION_JSON).content(userJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = restUserMockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    /**
     * Successfull deletion of comcast user.
     * @throws Exception
     */
    @Test
    public void deleteComcastUserTest() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/users/1")
                .accept(MediaType.APPLICATION_JSON).content(userJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = restUserMockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals("A user is deleted with identifier 1",response.getHeader("X-helloWorld-alert"));

    }


    /**
     * Get all comcast user test
     * @throws Exception
     */
    @Test
    public void getAllUserPosts() throws Exception {
        Mockito.when(
                userService.getUserMessagesFromCloud()).thenReturn(CompletableFuture.completedFuture(mockPosts));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);



        MvcResult result = restUserMockMvc.perform(requestBuilder)
                .andReturn();
                restUserMockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(DEFAULT_ID)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USERID)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
                .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)));

    }



}
