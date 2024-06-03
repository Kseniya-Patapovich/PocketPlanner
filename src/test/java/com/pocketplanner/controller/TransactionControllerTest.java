package com.pocketplanner.controller;

import com.pocketplanner.model.Transaction;
import com.pocketplanner.model.dto.TransactionCreateDto;
import com.pocketplanner.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransactions_Success() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    public void testGetTransactionsByAccountId_Success() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.getTransactionsByAccountId(1L)).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByAccountId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
    }

    @Test
    public void testGetTransactionById_Found() {
        Transaction transaction = new Transaction();
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction));

        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testGetTransactionById_NotFound() {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateTransaction_Success() {
        TransactionCreateDto transactionCreateDto = new TransactionCreateDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(transactionService.createTransaction(transactionCreateDto, 1L)).thenReturn(true);

        ResponseEntity<HttpStatus> response = transactionController.createTransactions(transactionCreateDto, 1L, bindingResult);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
