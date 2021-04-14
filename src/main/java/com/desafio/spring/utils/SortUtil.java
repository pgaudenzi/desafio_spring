package com.desafio.spring.utils;

import com.desafio.spring.dtos.ProductDto;

import java.util.Comparator;
import java.util.List;

public class SortUtil {

    private SortUtil () {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Sort products according to the criteria.
     * 0 - By ascending name
     * 1 - By descending name
     * 2 - By ascending price
     * 3 - By descending price
     * @param criteria
     * @param products
     * @return
     */
    public static List<ProductDto> sort(int criteria, List<ProductDto> products) {
        List<ProductDto> sortedProducts;

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
            default:
                throw new IllegalArgumentException("Invalid sorting criteria");
        }

        return sortedProducts;
    }

    private static List<ProductDto> sortByNameAsc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getName));
        return products;
    }

    private static List<ProductDto> sortByNameDesc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getName).reversed());
        return products;
    }

    private static List<ProductDto> sortByPriceAsc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getPrice));
        return products;
    }

    private static List<ProductDto> sortByPriceDesc(List<ProductDto> products) {
        products.sort(Comparator.comparing(ProductDto::getPrice).reversed());
        return products;
    }

}
