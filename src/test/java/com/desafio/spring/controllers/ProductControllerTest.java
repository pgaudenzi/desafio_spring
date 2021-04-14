package com.desafio.spring.controllers;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ProductService service;

    @Test
    void shouldReturnAllProducts() throws Exception {
        //Given
        List<ProductDto> products = getProducts("src/main/resources/test/ej1Products.json");

        //When
        when(service.getProducts(any())).thenReturn(products);
        MvcResult result = this.mockMvc.perform(get("/api/v1/articles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<ProductDto> response = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<List<ProductDto>>() {});

        //That
        assertEquals(products, response);

    }

    @Test
    void shouldReturnAllProductsWithCategoryTools() throws Exception {
        //Given
        List<ProductDto> products = getProducts("src/main/resources/test/ej2ProductsHerramientas.json");

        //When
        when(service.getProducts(any())).thenReturn(products);
        MvcResult result = this.mockMvc.perform(get("/api/v1/articles")
                .param("category", "Herramientas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<ProductDto> response = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<List<ProductDto>>() {});

        //That
        assertEquals(products, response);
    }

    private List<ProductDto> getProducts(String jsonPath) throws Exception {
        return objectMapper.readValue(
                new File(jsonPath),
                new TypeReference<List<ProductDto>>() {});
    }

}