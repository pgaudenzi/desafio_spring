package com.desafio.spring.exceptions;

public class ExistingUserException extends Exception {

    public ExistingUserException() {
        super("El usuario que desea dar de alta ya existe en el sistema");
    }
}
