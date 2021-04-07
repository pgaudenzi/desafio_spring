package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<ProductDto> products;
    private final AtomicInteger id = new AtomicInteger();

    public ProductRepositoryImpl() {
        this.products = loadDataBase();
    }

    @Override
    public List<ProductDto> getAll() {
        return this.products;
    }

    @Override
    public ProductDto findProductById(int id) throws ProductNotFoundException {
        List<ProductDto> products = this.products;
        ProductDto result = null;
        for (ProductDto product : products) {
            if (product.getId() == id) result = product;
        }
        if (result == null) throw new ProductNotFoundException(id);
        return result;
    }

    private List<ProductDto> loadDataBase() {
        FileReader fileReader = null;
        List<ProductDto> products = new ArrayList<>();

        try {
            fileReader = new FileReader("src/main/resources/dbProductos.csv");

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

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

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
