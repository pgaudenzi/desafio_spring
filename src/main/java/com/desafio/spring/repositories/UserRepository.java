package com.desafio.spring.repositories;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;
import com.desafio.spring.exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserRepository {

    void addNewUser(UserDto user) throws IOException, ExistingUserException;
    UserDto findUserByDni(String dni) throws UserNotFoundException;
    List<UserDto> getAll();
    boolean exists(String dni);

}
