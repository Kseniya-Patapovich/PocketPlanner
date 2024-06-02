package com.pocketplanner.service;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.AccountCreateDto;
import com.pocketplanner.repository.AccountRepository;
import com.pocketplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    static Account account = new Account();

    @BeforeAll
    public static void beforeAll() {
        account.setId(2L);
    }

    @Test
    void getAll_Success() {
        accountService.getAllAccounts();
        verify(accountRepository, times(1)).findAll();
    }

    /*@Test
    void createTest_Success() {
        Mockito.when(accountRepository.save(any())).thenReturn(account);
        accountService.createAccount(3L, new AccountCreateDto());
        Mockito.verify(accountRepository, Mockito.times(1)).save(any());
    }*/

    @Test
    public void testCreateAccount() {
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        accountCreateDto.setName("Test Account");
        accountCreateDto.setBalance(100.00);

        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setName("Test Account");
        account.setBalance(100.00);
        account.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Boolean isCreated = accountService.createAccount(1L, accountCreateDto);

        assertFalse(isCreated);
        verify(userRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
