package com.pocketplanner.controller;

import com.pocketplanner.exception.CustomValidException;
import com.pocketplanner.model.Transaction;
import com.pocketplanner.model.dto.TransactionCreateDto;
import com.pocketplanner.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/accountId/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("id") Long accountId) {
        return new ResponseEntity<>(transactionService.getTransactionsByAccountId(accountId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> createTransactions(@RequestBody TransactionCreateDto transactionCreateDto, @PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(transactionService.createTransaction(transactionCreateDto, id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable("id") Long id) {
        return new ResponseEntity<>(transactionService.deleteTransaction(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
