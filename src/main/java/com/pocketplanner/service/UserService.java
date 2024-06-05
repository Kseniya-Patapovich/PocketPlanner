package com.pocketplanner.service;

import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.UserCreateDto;
import com.pocketplanner.model.dto.UserUpdateAgeDto;
import com.pocketplanner.model.dto.UserUpdateNameDto;
import com.pocketplanner.model.dto.UserUpdatePassword;
import com.pocketplanner.repository.UserRepository;
import com.pocketplanner.security.model.Roles;
import com.pocketplanner.security.model.UserSecurity;
import com.pocketplanner.security.repository.UserSecurityRepository;
import com.pocketplanner.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
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

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserPassword(passwordEncoder.encode(userCreateDto.getUserPassword()));
        userSecurity.setUserLogin(userCreateDto.getUserLogin());
        userSecurity.setRole(Roles.USER);
        userSecurity.setUserId(user.getId());
        userSecurityRepository.save(userSecurity);

        return getUserById(createdUser.getId()).isPresent();
    }

    public Boolean updateUserName(UserUpdateNameDto userUpdateNameDto, Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            user.setUsername(userUpdateNameDto.getName());
            user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updatedUser = userRepository.saveAndFlush(user);
            return user.equals(updatedUser);
        }
        return false;
    }

    public Boolean updateUserAge(UserUpdateAgeDto userUpdateAgeDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAge(userUpdateAgeDto.getAge());
            user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updaredUser = userRepository.saveAndFlush(user);
            return user.equals(updaredUser);
        }
        return false;
    }

    public Boolean updatePassword(UserUpdatePassword userUpdatePassword, Long userId) {
        Optional<UserSecurity> userSecurityOptional = userSecurityRepository.findById(userId);
        if (userSecurityOptional.isPresent()) {
            UserSecurity userSecurity = userSecurityOptional.get();
            userSecurity.setUserPassword(passwordEncoder.encode(userUpdatePassword.getPassword()));
            UserSecurity updatedUser = userSecurityRepository.saveAndFlush(userSecurity);
            return userSecurity.equals(updatedUser);
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
