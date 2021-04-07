package com.desafio.spring.exceptions;

import com.desafio.spring.dtos.ProductDto;

public class NoAvailableStockException extends Exception {

    public NoAvailableStockException(ProductDto product) {
        super("El stock disponible para el producto " + product.getName() + ", no es suficiente para realizar la compra");
    }
}
