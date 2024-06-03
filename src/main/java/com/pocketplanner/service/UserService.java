package com.pocketplanner.service;

import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.UserCreateDto;
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
        userSecurity.setIsBlocked(false);
        userSecurity.setUserId(user.getId());
        userSecurityRepository.save(userSecurity);

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
