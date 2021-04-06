package com.desafio.spring.controllers;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/articles")
    public ResponseEntity<List<ProductDto>> getArticles( @RequestParam(required = false) Map<String, String> params)
            throws IllegalArgumentException {

        return new ResponseEntity<>(service.getProducts(params), HttpStatus.OK);
    }

}
