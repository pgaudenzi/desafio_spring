package com.desafio.spring.services;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void createUser(UserDto user) throws IOException, ExistingUserException;

    List<UserDto> getAllUsers(String province);
}
