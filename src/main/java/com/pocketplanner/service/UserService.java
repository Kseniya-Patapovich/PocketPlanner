package com.pocketplanner.service;

import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.UserCreateDto;
import com.pocketplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setAge(userCreateDto.getAge());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User createdUser = userRepository.save(user);
        return getUserById(createdUser.getId()).isPresent();
    }

    public Boolean updateUser(User user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            if (userFromDB.getUsername() != null) {
                userFromDB.setUsername(user.getUsername());
            }
            if (userFromDB.getAge() != null) {
                userFromDB.setAge(user.getAge());
            }
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return userFromDB.equals(updateUser);
        }
        return false;
    }

    public Boolean deleteUser(Long id) {
        Optional<User> userCheck = getUserById(id);
        if (userCheck.isEmpty()) {
            return false;
        }
        userRepository.deleteById(id);
        return getUserById(id).isEmpty();
    }
}
