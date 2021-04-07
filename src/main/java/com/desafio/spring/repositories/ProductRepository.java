package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductRepository {

    List<ProductDto> getAll();
    ProductDto findProductById(int id) throws ProductNotFoundException;

}
