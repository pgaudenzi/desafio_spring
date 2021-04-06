package com.desafio.spring.repositories;

import com.desafio.spring.dtos.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository {

    List<ProductDto> getAll();

}
