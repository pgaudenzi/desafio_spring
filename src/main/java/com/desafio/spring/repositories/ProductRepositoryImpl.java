package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<ProductDto> getAll() {
        return loadDataBase();
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

        String id, name, category, brand, price, quantity, freeShipping, prestige;

        id = data[0];
        name = data[1];
        category = data[2];
        brand = data[3];
        price = data[4];
        quantity = data[5];
        freeShipping = data[6];
        prestige = data[7];

        return new ProductDto(Integer.parseInt(id), name, category.toLowerCase(), brand,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                Integer.parseInt(quantity),
                freeShipping, prestige);

    }

}
