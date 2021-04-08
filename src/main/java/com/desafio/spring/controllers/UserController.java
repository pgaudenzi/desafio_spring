package com.desafio.spring.controllers;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;
import com.desafio.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/add-client")
    public ResponseEntity<String> createUser(@RequestBody UserDto user)
            throws IOException, ExistingUserException {

        service.createUser(user);
        return new ResponseEntity<>("Nuevo cliente dado de alta", HttpStatus.OK);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<UserDto>> getClients(@RequestParam(required = false) String province) {
        return new ResponseEntity<>(service.getAllUsers(province), HttpStatus.OK);
    }


}
