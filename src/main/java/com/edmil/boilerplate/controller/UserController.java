package com.edmil.boilerplate.controller;

import com.edmil.boilerplate.service.UserService;
import com.edmil.boilerplate.exception.customexceptions.UserAlreadyExists;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edmil.boilerplate.model.User;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return service.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) throws UserAlreadyExists {
        return service.signup(user);
    }

}