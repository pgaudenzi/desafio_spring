package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;

public interface SortRepository {

    List<ProductDto> sort (int criteria, List<ProductDto> products);

}
