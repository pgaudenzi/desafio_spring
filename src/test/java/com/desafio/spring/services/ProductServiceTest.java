package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.repositories.ProductRepositoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ProductServiceTest {

    private List<ProductDto> products;

    @Autowired
    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepositoryImpl repository;


    @BeforeEach
    public void setUp() throws IOException {
        products = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        products = objectMapper.readValue(
                new File("src/main/resources/test/ej3Products.json"),
                new TypeReference<List<ProductDto>>() {});
    }

    @Test
    void shouldReturnAllProducts() {
        //Given
        Map<String, String> params = new HashMap<>();

        //when
        when(repository.getAll()).thenReturn(products);
        List<ProductDto> result = service.getProducts(params);

        //then
        assertEquals(result, products);
    }

    @Test
    void shouldReturnProductsFilteredWithTwoFilters() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("category", "herramientas");
        params.put("prestige", "****");
        final List<ProductDto> expectedProducts = new ArrayList<>();
        expectedProducts.add(new ProductDto
                (1,"Desmalezadora", "Herramientas", "Makita",
                        9600, 5, "SI", "****"));
        expectedProducts.add(new ProductDto
                (2,"Taladro", "Herramientas", "Black & Decker",
                        12500, 7, "SI", "****"));

        //when
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //then
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
    }

    @Test
    void shouldFilterOnlyByCategory() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("category", "herramientas");

        //when
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //then
        assertEquals(3, result.size());
    }

    @Test
    void shouldFilterByCategoryAndSort() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("category", "herramientas");
        params.put("order", "0");

        //when
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //then
        assertEquals(3, result.size());
        assertEquals("Desmalezadora", result.get(0).getName());
    }

    @Test
    void shouldSortProductsByNameAsc() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("order", "0");

        //When
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //Then
        assertEquals("Camiseta", result.get(0).getName());
        assertEquals("Zapatillas Deportivas", result.get(result.size()-1).getName());
    }

    @Test
    void shouldSortProductsByNameDes() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("order", "1");

        //When
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //Then
        assertEquals( "Zapatillas Deportivas", result.get(0).getName());
        assertEquals("Camiseta", result.get(result.size()-1).getName());
    }

    @Test
    void shouldSortProductsByPriceAsc() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("order", "2");

        //When
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //Then
        assertEquals( 500, result.get(0).getPrice());
        assertEquals(40000, result.get(result.size()-1).getPrice());
    }

    @Test
    void shouldSortProductsByPriceDes() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("order", "3");

        //When
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //Then
        assertEquals( 40000, result.get(0).getPrice());
        assertEquals(500, result.get(result.size()-1).getPrice());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTheOnlyFilterIsNotCategory() {
        //Given
        boolean thrown = false;
        String errorMessage = "";
        Map<String, String> params = new HashMap<>();
        params.put("prestige", "****");

        //When
        when(repository.getAll()).thenReturn(products);
        try {
            service.getProducts(params);
        } catch (IllegalArgumentException e) {
            thrown = true;
            errorMessage = e.getMessage();
        }

        //Then
        assertTrue(thrown);
        assertEquals("Products can only be filtered by category", errorMessage);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenThereAreMoreThanTwoFilters() {
        //Given
        boolean thrown = false;
        String errorMessage = "";
        Map<String, String> params = new HashMap<>();
        params.put("prestige", "****");
        params.put("category", "herramientas");
        params.put("freeShipping", "true");

        //When
        when(repository.getAll()).thenReturn(products);
        try {
            service.getProducts(params);
        } catch (IllegalArgumentException e) {
            thrown = true;
            errorMessage = e.getMessage();
        }

        //Then
        assertTrue(thrown);
        assertEquals("Invalid amount of arguments, only two filters allowed", errorMessage);
    }

    @Test
    void shouldThrowInvalidSortingCriteriaException() {
        //Given
        boolean thrown = false;
        String errorMessage = "";
        Map<String, String> params = new HashMap<>();
        params.put("order", "5");

        //When
        when(repository.getAll()).thenReturn(products);
        try {
            service.getProducts(params);
        } catch (IllegalArgumentException e) {
            thrown = true;
            errorMessage = e.getMessage();
        }

        //Then
        assertTrue(thrown);
        assertEquals("Invalid sorting criteria", errorMessage);
    }

    @Test
    void shouldReturnNothingIfTheFilterIsNonexistent() {
        //Given
        Map<String, String> params = new HashMap<>();
        params.put("category", "herramientas");
        params.put("nonexistent", "****");

        //when
        when(repository.getAll()).thenReturn(products);
        final List<ProductDto> result = service.getProducts(params);

        //then
        assertTrue(result.isEmpty());
    }

}