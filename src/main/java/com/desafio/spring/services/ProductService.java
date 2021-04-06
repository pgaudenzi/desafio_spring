package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductDto> getProducts(Map<String, String> params) throws IllegalArgumentException;

}
