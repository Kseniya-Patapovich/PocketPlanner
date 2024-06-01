package com.pocketplanner.service;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.Transaction;
import com.pocketplanner.model.dto.TransactionCreateDto;
import com.pocketplanner.repository.AccountRepository;
import com.pocketplanner.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getTransactionsByAccountId(Long id) {
        return transactionRepository.findAllByAccount_Id(id);
    }

    public Boolean createTransaction(TransactionCreateDto transactionCreateDto, Long accountId) {
        Transaction transaction = new Transaction();
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            return false;
        }
        transaction.setAmount(transactionCreateDto.getAmount());
        transaction.setAccount(account.get());
        account.get().setBalance(account.get().getBalance() - transactionCreateDto.getAmount());
        transaction.setDate(Timestamp.valueOf(LocalDateTime.now()));
        Transaction createdTransaction = transactionRepository.save(transaction);
        return getTransactionById(createdTransaction.getId()).isPresent();
    }

    public Boolean deleteTransaction(Long id) {
        Optional<Transaction> checkTransaction = getTransactionById(id);
        if (checkTransaction.isEmpty()) {
            return false;
        }
        transactionRepository.deleteById(id);
        return getTransactionById(id).isEmpty();
    }
}
