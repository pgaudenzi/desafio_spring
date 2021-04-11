package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserShoppingCartDto {

    private double total;
    private List<PurchaseReqResponseDto> purchases;

}
