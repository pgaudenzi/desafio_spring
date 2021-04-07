package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class StatusCodeDto {

    private HttpStatus status;
    private String message;

}
