package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseReqResponseDto {

    private TicketDto ticket;
    private StatusCodeDto statusCode;

}
