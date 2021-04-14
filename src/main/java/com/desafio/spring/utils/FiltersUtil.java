package com.desafio.spring.utils;

import com.desafio.spring.dtos.ProductDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiltersUtil {

    private FiltersUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Validates if filters comply with business rules.
     * A max of two filters allowed.
     * If only one filter is present, it must be only the category filter.
     * @param filters
     * @throws IllegalArgumentException
     */
    public static void validate(Map<String, String> filters) throws IllegalArgumentException {
        if (filters.size() > 2)
            throw new IllegalArgumentException("Invalid amount of arguments, only two filters allowed");
        if (filters.size() == 1 && !filters.containsKey("category"))
            throw new IllegalArgumentException("Products can only be filtered by category");
    }

    /**
     * Method to filter the products with two filters.
     * @param products
     * @param filters
     * @return
     */
    public static List<ProductDto> filterProductsWithTwoFilters(List<ProductDto> products, Map<String, String> filters) {
        Object[] keys = filters.keySet().toArray();
        return products.stream()
                .filter(product -> getAttribute(keys[0].toString(), product).equalsIgnoreCase(filters.get(keys[0].toString()))
                        && getAttribute(keys[1].toString(), product).equalsIgnoreCase(filters.get(keys[1].toString())))
                .collect(Collectors.toList());
    }

    /**
     * Method to filter only by category
     * @param category
     * @param products
     * @return
     */
    public static List<ProductDto> filterByCategory(String category, List<ProductDto> products) {
        return products.stream()
                .filter(product -> (product.getCategory()).equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Aux method to get the value in the specified attribute
     * @param attributeName
     * @param product
     * @return
     */
    private static String getAttribute(String attributeName, ProductDto product) {

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
            default:
                return "";
        }
    }

}
