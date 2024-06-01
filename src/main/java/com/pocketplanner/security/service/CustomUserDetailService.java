package com.pocketplanner.security.service;

import com.pocketplanner.security.model.UserSecurity;
import com.pocketplanner.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public CustomUserDetailService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserSecurity> userSecurityInfoOptional = userSecurityRepository.findByUserLogin(username);
        if (userSecurityInfoOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        UserSecurity userSecurity = userSecurityInfoOptional.get();
        return User.builder()
                .username(userSecurity.getUserLogin())
                .password(userSecurity.getUserPassword())
                .roles(userSecurity.getRole().toString())
                .build();
    }
}
