package com.pocketplanner.security.service;

import com.pocketplanner.exception.SameUserInDatabase;
import com.pocketplanner.model.User;
import com.pocketplanner.repository.UserRepository;
import com.pocketplanner.security.model.Roles;
import com.pocketplanner.security.model.UserSecurity;
import com.pocketplanner.security.model.dto.AuthRequestDto;
import com.pocketplanner.security.model.dto.RegistrationDto;
import com.pocketplanner.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSecurityService {
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(PasswordEncoder passwordEncoder, UserSecurityRepository userSecurityRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userSecurityRepository = userSecurityRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public void registration(RegistrationDto registrationDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        User user = new User();
        user.setAge(registrationDto.getAge());
        user.setUsername(registrationDto.getUsername());
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        User savedUser = userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserLogin(registrationDto.getLogin());
        userSecurity.setUserPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setUserId(savedUser.getId());
        userSecurity.setRole(Roles.USER);
        //userSecurity.setIsBlocked(false);
        userSecurityRepository.save(userSecurity);
    }

    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(authRequestDto.getLogin());
        if (security.isPresent() && passwordEncoder.matches(authRequestDto.getPassword(), security.get().getUserPassword())) {
            return Optional.of(jwtUtils.generateJwtToken(authRequestDto.getLogin()));
        }
        return Optional.empty();
    }
}
