package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemTicketDto {

    private int productId;
    private String name;
    private String brand;
    private int quantity;

}
