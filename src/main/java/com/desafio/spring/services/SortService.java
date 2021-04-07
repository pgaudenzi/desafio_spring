package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;

public interface SortService {

    List<ProductDto> sort (int criteria, List<ProductDto> products);

}
