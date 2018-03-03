package com.comcast.helloworld.controller;


import com.comcast.helloworld.dto.UserDTO;
import com.comcast.helloworld.dto.UserPost;
import com.comcast.helloworld.service.UserService;
import com.comcast.helloworld.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
public class UserController {


    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserService userService;


    /**
     * GET /users : Returns all comcast users
     * <p>
     * Returns all Comcast users.
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllComcastUsers(){
        log.debug("Rest request to get all Comcast users.");
        return new ResponseEntity<>(userService.getAllComcastUsers(),null , HttpStatus.OK);
    }


    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the username and email are not already used
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("New user cannot have id",userDTO.getId().toString())).build();
        } else if (userService.findUserByEmailIgnoreCase(userDTO.getUsername().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("Username already exists",userDTO.getUsername())).build();
        } else if (userService.findUserByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("Email already exists",userDTO.getEmail())).build();
        } else {
            UserDTO newUser = userService.createComcastUser(userDTO);
            return ResponseEntity.created(new URI("/api/users/"))
                    .headers(HeaderUtil.createAlert( "A comcast user is created with identifier " + newUser.getUsername(), newUser.getUsername()))
                    .body(newUser);
        }
    }

    /**
     * DELETE /users/:id -> delete the "login" User.
     *
     * @param id the id of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteComcastUser(@PathVariable Long id) {
        log.debug("REST request to delete User: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + id, id.toString())).build();
    }


    /**
     * POST /users/messages -> Get All user messages
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/users/posts")
    public CompletableFuture<List<UserPost>> getAllUserMessages() {
        log.debug("REST request to get all user messages");
        log.info("Servlet Thread Id = " + Thread.currentThread().getName());
        CompletableFuture<List<UserPost>> userPosts = new CompletableFuture<>();
        try {
            userPosts.complete(userService.getUserMessagesFromCloud().get());
        } catch (Exception e) {
            log.error("Error while making external API call {}", e);
            userPosts.completeExceptionally(e);
        }
        return userPosts;
    }

}
