package com.pocketplanner.repository;

import com.pocketplanner.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        user.setUsername("USERNAME");
        user.setAge(22);
    }

    @Test
    void findAllTest_Success() {
        List<User> users = userRepository.findAll();
        Assertions.assertNotNull(users);
    }

    @Test
    void findByIdTest_Success() {
        User userFromDb = userRepository.findAll().get(0);
        Optional<User> userOptional = userRepository.findById(userFromDb.getId());
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    void saveTest_Success() {
        User savedUser = userRepository.save(user);
        Optional<User> userOptional = userRepository.findById(savedUser.getId());
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    void updateTest_Success() {
        User savedUser = userRepository.save(user);
        savedUser.setUsername("NEW_USERNAME");
        User resultUser = userRepository.saveAndFlush(savedUser);
        Assertions.assertEquals(resultUser.getUsername(), "NEW_USERNAME");
    }

    @Test
    void deleteByIdTest_Success() {
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        Optional<User> resultUser = userRepository.findById(savedUser.getId());
        Assertions.assertFalse(resultUser.isPresent());
    }
}
