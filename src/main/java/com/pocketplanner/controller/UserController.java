package com.pocketplanner.controller;

import com.pocketplanner.exception.CustomValidException;
import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.UserCreateDto;
import com.pocketplanner.model.dto.UserUpdateAgeDto;
import com.pocketplanner.model.dto.UserUpdateNameDto;
import com.pocketplanner.model.dto.UserUpdatePassword;
import com.pocketplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(userService.createUser(userCreateDto) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<HttpStatus> updateUsername(@RequestBody UserUpdateNameDto userUpdateNameDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.updateUserName(userUpdateNameDto, id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }


    @PutMapping("/age/{id}")
    public ResponseEntity<HttpStatus> updateUserAge(@RequestBody UserUpdateAgeDto userUpdateAgeDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.updateUserAge(userUpdateAgeDto, id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<HttpStatus> updatePassword(@RequestBody UserUpdatePassword userUpdatePassword, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.updatePassword(userUpdatePassword, id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUser(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
