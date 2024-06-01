package com.pocketplanner.controller;

import com.pocketplanner.exception.CustomValidException;
import com.pocketplanner.model.Account;
import com.pocketplanner.model.dto.AccountCreateDto;
import com.pocketplanner.service.AccountService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        Optional<Account> accountOptional = accountService.getAccountById(id);
        if (accountOptional.isPresent()) {
            return new ResponseEntity<>(accountOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> createAccount(@PathVariable("id") Long userId, @RequestBody AccountCreateDto accountCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(accountService.createAccount(userId, accountCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.deleteAccount(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
