package com.desafio.spring.exceptions;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(int id) {
        super("El producto con ID " + id + " no existe");
    }
}
