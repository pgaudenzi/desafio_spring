package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PorductRepositoryImpl implements ProductRepository {

    @Override
    public List<ProductDto> getAll() {
        return loadDataBase();
    }

    private List<ProductDto> loadDataBase() {
        FileReader fileReader = null;
        List<ProductDto> products = new ArrayList<>();

        try {
            fileReader = new FileReader("src/main/resources/dbProductos.csv");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        BufferedReader csvReader = new BufferedReader(fileReader);

        try {
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

        return new ProductDto(name, category, brand,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                Integer.parseInt(quantity),
                freeShipping, prestige);

    }

}
