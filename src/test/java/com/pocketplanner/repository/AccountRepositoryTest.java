package com.pocketplanner.repository;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    static Account account;

    @BeforeAll
    static void beforeAll() {
        account = new Account();
        account.setName("TestName");
        account.setBalance(1234.0);
        account.setUser(new User());
    }

    @Test
    void findAllTest_Success() {
        List<Account> accounts = accountRepository.findAll();
        Assertions.assertNotNull(accounts);
    }

    @Test
    void findByIdTest_Success() {
        Account accountFromDb = accountRepository.findAll().get(0);
        Optional<Account> accountOptional = accountRepository.findById(accountFromDb.getId());
        Assertions.assertTrue(accountOptional.isPresent());
    }
}
