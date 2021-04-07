package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FilterRepositoryImpl implements FilterRepository {

    @Override
    public void validate(Map<String, String> filters) throws IllegalArgumentException {
        if (filters.size() > 2)
            throw new IllegalArgumentException("Invalid amount of arguments, only two filters allowed");
        if (filters.size() == 1 && !filters.containsKey("category"))
            throw new IllegalArgumentException("Products can only be filtered by category");
    }

    @Override
    public List<ProductDto> filterProductsWithTwoFilters(List<ProductDto> products, Map<String, String> filters) {
        Object[] keys = filters.keySet().toArray();
        return products.stream()
                .filter(product -> getAttribute(keys[0].toString(), product).equalsIgnoreCase(filters.get(keys[0].toString()))
                        && getAttribute(keys[1].toString(), product).equalsIgnoreCase(filters.get(keys[1].toString())))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> filterByCategory(String category, List<ProductDto> products) {
        return products.stream()
                .filter((product) -> (product.getCategory()).equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    private String getAttribute(String attributeName, ProductDto product) {

        switch (attributeName) {
            case "category":
                return product.getCategory();
            case "product":
                return product.getName();
            case "brand":
                return product.getBrand();
            case "freeShipping":
                return product.getFreeShipping().equals("SI") ? "true" : "false";
            case "price":
                return Integer.toString(product.getPrice());
            case "prestige":
                return product.getPrestige();
        }

        return "";
    }

}
