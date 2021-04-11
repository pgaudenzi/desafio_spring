package com.desafio.spring.config;

import com.desafio.spring.dtos.ErrorDto;
import com.desafio.spring.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * Manage all the exceptions
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgument(IllegalArgumentException e) {
        ErrorDto error = new ErrorDto("IllegalArgumentException", e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(ProductNotFoundException e) {
        ErrorDto error = new ErrorDto("ProductNotFoundException",
                "No se puede generar la solicitud de compra, causa: " + e.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(NoAvailableStockException.class)
    public ResponseEntity<ErrorDto> handleNotAvailableStock(NoAvailableStockException e) {
        ErrorDto error = new ErrorDto("NoAvailableStockException",
                "No se puede generar la solicitud de compra, causa: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(UserNotFoundException e) {
        ErrorDto error = new ErrorDto("UserNotFoundException",
                 e.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ErrorDto> handleExistingUser(ExistingUserException e) {
        ErrorDto error = new ErrorDto("ExistingUserException",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handleIOException(IOException e) {
        ErrorDto error = new ErrorDto("IOException",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(BadUserException.class)
    public ResponseEntity<ErrorDto> handleIOException(BadUserException e) {
        ErrorDto error = new ErrorDto("BadUserException",
                "The user does not exists",
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getStatus());
    }

}
