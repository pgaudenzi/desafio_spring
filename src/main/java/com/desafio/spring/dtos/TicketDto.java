package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private int id;
    private List<ItemTicketDto> articles;
    private double total;

}
