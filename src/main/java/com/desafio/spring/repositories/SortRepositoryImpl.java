package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class SortRepositoryImpl implements SortRepository {

    public List<ProductDto> sort(int criteria, List<ProductDto> products) {
        List<ProductDto> sortedProducts = new ArrayList<>();

        switch (criteria) {
            case 0:
                sortedProducts = sortByNameAsc(products);
                break;
            case 1:
                sortedProducts = sortByNameDesc(products);
                break;
            case 2:
                sortedProducts = sortByPriceAsc(products);
                break;
            case 3:
                sortedProducts = sortByPriceDesc(products);
                break;
        }

        return sortedProducts;
    }

    private List<ProductDto> sortByNameAsc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getName));
        return products;
    }

    private List<ProductDto> sortByNameDesc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getName).reversed());
        return products;
    }

    private List<ProductDto> sortByPriceAsc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getPrice));
        return products;
    }

    private List<ProductDto> sortByPriceDesc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getPrice).reversed());
        return products;
    }

}
