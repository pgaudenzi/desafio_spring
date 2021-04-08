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
        List<ProductDto> products = this.products;
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
        FileReader fileReader = null;
        String absPath = new File("").getAbsolutePath();
        List<ProductDto> products = new ArrayList<>();

        try {
            fileReader = new FileReader(absPath + "/src/main/resources/dbProductos.csv");

            BufferedReader csvReader = new BufferedReader(fileReader);

            String row;
            boolean firstTime = true;
            while ((row = csvReader.readLine()) != null) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    String[] data = row.split(",");
                    products.add(objectMapper(data));
                }
            }
            csvReader.close();

        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

        return products;
    }

    /**
     * Aux method to convert a line from the csv to an object
     * @param data
     * @return
     */
    private ProductDto objectMapper(String[] data) {

        String name, category, brand, price, quantity, freeShipping, prestige;

        name = data[0];
        category = data[1];
        brand = data[2];
        price = data[3];
        quantity = data[4];
        freeShipping = data[5];
        prestige = data[6];

        return new ProductDto(id.incrementAndGet(), name, category, brand,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                Integer.parseInt(quantity),
                freeShipping, prestige);

    }

}
