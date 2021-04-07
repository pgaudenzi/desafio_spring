package com.desafio.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String name;
    private String description;
    private HttpStatus status;

}
