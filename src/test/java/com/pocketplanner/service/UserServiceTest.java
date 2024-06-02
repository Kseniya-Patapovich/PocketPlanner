package com.pocketplanner.service;

import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.UserCreateDto;
import com.pocketplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    static User user = new User();

    @BeforeAll
    public static void beforeAll() {
        user.setId(5L);
    }

    @Test
    void getAll_Success() {
        userService.getAllUsers();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createTest_Success() {
        Mockito.when(userRepository.save(any())).thenReturn(user);
        userService.createUser(new UserCreateDto());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void deleteUserById_Success() {
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        userService.deleteUser(132L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyLong());
    }
}
