package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();

}
