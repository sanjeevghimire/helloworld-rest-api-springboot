package com.comcast.helloworld.service;


import com.comcast.helloworld.domain.User;
import com.comcast.helloworld.dto.UserDTO;
import com.comcast.helloworld.dto.UserPost;
import com.comcast.helloworld.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class UserService {


    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${application.restUrl.userMessage}")
    private String externalUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * Get all comcast users.
     * @return
     */
    public List<UserDTO> getAllComcastUsers() {
        log.debug("Getting all comcast users");
        List<UserDTO> users = new LinkedList<>();
        userRepository.findAll().forEach(user -> users.add(new UserDTO(user)));

        return users;
    }


    /**
     * Register new user
     * @param userDTO
     * @return
     */
    public UserDTO createComcastUser(UserDTO userDTO){
        log.debug("Creating new user {}", userDTO);
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);
        log.debug("User saved with id : {}", user.getId());

        userDTO.setId(user.getId());

        return userDTO;

    }

    /**
     * Delete a user
     * @param id
     */
    public void deleteUser(Long id) {
        userRepository.findOneById(id).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }


    /**
     * Get user by username
     * @param username
     * @return
     */
    public Optional<User> findUserByUsername(String username){
        return userRepository.findOneByUsername(username);
    }


    /**
     * get user by email
     * @param email
     * @return
     */
    public Optional<User> findUserByEmailIgnoreCase(String email){
        return userRepository.findOneByEmailIgnoreCase(email);
    }


    /**
     * Method to asynchronously call external API
     * @return
     * @throws InterruptedException
     */
    @Async
    public CompletableFuture<List<UserPost>> getUserMessagesFromCloud() throws InterruptedException{
        log.info("Fetching user messages from cloud");
        UserPost[] results = restTemplate.getForObject(externalUrl, UserPost[].class);
        // delay of 1s
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(Arrays.asList(results));
    }





}
