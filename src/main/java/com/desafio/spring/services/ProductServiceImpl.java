package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    FilterService filterService;

    @Override
    public List<ProductDto> getProducts(Map<String, String> params) throws IllegalArgumentException {

        if (params.isEmpty()) return repository.getAll();

        if (params.containsKey("sort")) {
            int sortCriteria = Integer.parseInt(params.get("sort"));
            params.remove("sort");
        }

        filterService.validate(params);
        List<ProductDto> products = repository.getAll();

        if (params.size() == 2) {
            return filterService.filterProductsWithMultipleFilters(products, params);
        } else {
            return filterService.filterByCategory(params.get("category"), products);
        }
    }

}
