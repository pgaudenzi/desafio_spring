package com.desafio.spring.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequestDto {

    private List<ItemTicketDto> articles;

}
