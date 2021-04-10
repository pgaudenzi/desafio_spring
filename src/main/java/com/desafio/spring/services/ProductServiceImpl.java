package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.repositories.FilterRepository;
import com.desafio.spring.repositories.ProductRepository;
import com.desafio.spring.repositories.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service's implementation to manage the logic to get the products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final String ORDER = "order";

    @Autowired
    ProductRepository repository;

    @Autowired
    FilterRepository filterRepository;

    @Autowired
    SortRepository sortRepository;

    /**
     * Method to get the products.
     * If no params are received from the user, it return all the available products.
     * If only the 'order' param is received, first it get all the products and then return the a sorted list.
     * If there are filters, first it filter the products, if it's needed it sort, and then return a filtered list.
     * @param params
     * @return the processed products according to the params received.
     * @throws IllegalArgumentException
     */
    @Override
    public List<ProductDto> getProducts(Map<String, String> params) throws IllegalArgumentException {
        List<ProductDto> result;
        boolean sort = false;
        int sortCriteria = 0;

        // Remove the order value to manage the filters logic first
        if (params.containsKey(ORDER)) {
            sort = true;
            sortCriteria = Integer.parseInt(params.get(ORDER));
            params.remove(ORDER);
        }

        List<ProductDto> products = repository.getAll();

        if (params.isEmpty()) {
            if (sort) {
                return sortRepository.sort(sortCriteria, products);
            }
            return products;
        }

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
