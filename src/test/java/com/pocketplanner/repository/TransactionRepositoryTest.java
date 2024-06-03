package com.pocketplanner.repository;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.Transaction;
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
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;
    static Transaction transaction;

    @BeforeAll
    static void beforeAll() {
        transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setDate(Timestamp.valueOf(LocalDateTime.now()));
        transaction.setAccount(new Account());
    }

    @Test
    void findAllTest_Success() {
        List<Transaction> accounts = transactionRepository.findAll();
        Assertions.assertNotNull(accounts);
    }

    @Test
    void findByIdTest_Success() {
        Transaction transactionFromDb = transactionRepository.findAll().get(0);
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionFromDb.getId());
        Assertions.assertTrue(transactionOptional.isPresent());
    }
}
