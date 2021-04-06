package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String category;
    private String brand;
    private int price;
    private int quantity;
    private String freeShipping;
    private String prestige;

}
