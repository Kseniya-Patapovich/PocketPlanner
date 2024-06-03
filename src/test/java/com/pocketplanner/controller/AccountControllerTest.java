package com.pocketplanner.controller;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.dto.AccountCreateDto;
import com.pocketplanner.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/*@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)*/
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        when(accountService.getAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<Account>> response = accountController.getAllAccount();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accounts, response.getBody());
    }

    @Test
    public void testCreateAccountSuccess() {
        AccountCreateDto accountCreateDto = new AccountCreateDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(accountService.createAccount(1L, accountCreateDto)).thenReturn(true);

        ResponseEntity<HttpStatus> response = accountController.createAccount(1L, accountCreateDto, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteAccountSuccess() {
        when(accountService.deleteAccount(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> response = accountController.deleteAccount(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
