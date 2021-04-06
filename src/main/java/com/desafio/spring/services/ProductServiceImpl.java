package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public List<ProductDto> getProducts() {
        return repository.getAll();
    }
}
