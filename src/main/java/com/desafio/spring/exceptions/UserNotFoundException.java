package com.desafio.spring.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String dni) {
        super("No se encuentra el cliente con DNI: " + dni);
    }
}
