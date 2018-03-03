package com.comcast.helloworld.repository;

import com.comcast.helloworld.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findOneById(Long id);

    Optional<User> findOneByUsername(String s);

    Optional<User> findOneByEmailIgnoreCase(String email);
}
