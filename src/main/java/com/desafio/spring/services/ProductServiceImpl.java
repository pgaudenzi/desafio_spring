package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.repositories.FilterRepository;
import com.desafio.spring.repositories.ProductRepository;
import com.desafio.spring.repositories.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    FilterRepository filterRepository;

    @Autowired
    SortRepository sortRepository;

    @Override
    public List<ProductDto> getProducts(Map<String, String> params) throws IllegalArgumentException {
        List<ProductDto> result;
        boolean sort = false;
        int sortCriteria = 0;

        if (params.isEmpty())
            return repository.getAll();

        if (params.containsKey("order")) {
            sort = true;
            sortCriteria = Integer.parseInt(params.get("order"));
            params.remove("order");
        }

        List<ProductDto> products = repository.getAll();
        filterRepository.validate(params);

        if (params.size() == 2) {
            result = filterRepository.filterProductsWithTwoFilters(products, params);
        } else {
            result = filterRepository.filterByCategory(params.get("category"), products);
        }

        if (sort) {
            return sortRepository.sort(sortCriteria, result);
        }

        return result;
    }


}
