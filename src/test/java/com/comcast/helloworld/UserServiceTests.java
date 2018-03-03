package com.comcast.helloworld;


import com.comcast.helloworld.domain.User;
import com.comcast.helloworld.dto.UserDTO;
import com.comcast.helloworld.dto.UserPost;
import com.comcast.helloworld.repository.UserRepository;
import com.comcast.helloworld.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class UserServiceTests {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    private User user;
    private static final Long USERID_NOT_EXIST = 23L;

    @Before
    public void init() {
        user = new User();
        user.setUsername("johndoe");
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");

    }

    @Test
    public void assertThatCreateComcastUserReturnsUserWithId(){
        UserDTO userDTO = userService.createComcastUser(new UserDTO(user));
        assertThat(userDTO.getId()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void assertThatDatabaseUserSizeMatchestheResultSize(){
        userRepository.saveAndFlush(user);
        assertThat(userService.getAllComcastUsers().size()).isGreaterThan(0);
        // its 3 because 2 entries are already added during startup
        assertThat(userService.getAllComcastUsers().size()).isEqualTo(3);
    }


    @Test
    public void assertThatDeleteGiveIDThatDoesntExist(){
        userRepository.saveAndFlush(user);
        userService.deleteUser(user.getId());
        Optional<User> userO =  userService.findUserById(user.getId());
        assertThat(userO).isEqualTo(Optional.empty());
    }



    @Test
    public void assertExternalCallToGetPosts() throws InterruptedException, ExecutionException {
        List<UserPost> posts = userService.getUserMessagesFromCloud().get();
        assertThat(posts).size().isGreaterThan(0);
    }


    @Test
    public void assertThatFindingUserByIdThatDoesntExistGivesEmptyResult(){
       Optional<User> user =  userService.findUserById(USERID_NOT_EXIST);
       assertThat(user).isEqualTo(Optional.empty());
    }







}
