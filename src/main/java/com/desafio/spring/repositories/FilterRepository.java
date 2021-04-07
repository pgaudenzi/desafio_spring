package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;
import java.util.Map;

public interface FilterRepository {

    void validate(Map<String, String> filters) throws IllegalArgumentException;

    List<ProductDto> filterProductsWithTwoFilters(List<ProductDto> products, Map<String, String> filters);

    List<ProductDto> filterByCategory(String category, List<ProductDto> products);

}
