package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation to get the products stored in csv file.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<ProductDto> products;
    private final AtomicInteger id = new AtomicInteger();

    /**
     * Constructor - needed to maintain the products in memory and manage the stock from there
     */
    public ProductRepositoryImpl() {
        this.products = loadDataBase();
    }

    /**
     * @return all products
     */
    @Override
    public List<ProductDto> getAll() {
        return this.products;
    }

    /**
     * @param id
     * @return the product matching with the id passed by parameter
     * @throws ProductNotFoundException
     */
    @Override
    public ProductDto findProductById(int id) throws ProductNotFoundException {
        ProductDto result = null;
        for (ProductDto product : products) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        if (result == null) throw new ProductNotFoundException(id);
        return result;
    }

    /**
     * Aux method to get the products from the csv
     * @return
     */
    private List<ProductDto> loadDataBase() {
        String absPath = new File("").getAbsolutePath();
        List<ProductDto> allProducts = new ArrayList<>();

        try (BufferedReader csvReader = new BufferedReader(
                new FileReader(absPath + "/src/main/resources/dbProductos.csv"))) {

            String row;
            boolean firstTime = true;
            while ((row = csvReader.readLine()) != null) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    String[] data = row.split(",");
                    allProducts.add(objectMapper(data));
                }
            }
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

        return allProducts;
    }

    /**
     * Aux method to convert a line from the csv to an object
     * @param data
     * @return
     */
    private ProductDto objectMapper(String[] data) {

        String name = data[0];
        String category = data[1];
        String brand = data[2];
        String price = data[3];
        String quantity = data[4];
        String freeShipping = data[5];
        String prestige = data[6];

        return new ProductDto(id.incrementAndGet(), name, category, brand,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                Integer.parseInt(quantity),
                freeShipping, prestige);

    }

}
