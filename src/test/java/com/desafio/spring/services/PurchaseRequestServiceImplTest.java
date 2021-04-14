package com.desafio.spring.services;

import com.desafio.spring.dtos.ProductDto;
import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;
import com.desafio.spring.exceptions.BadUserException;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.repositories.ProductRepository;
import com.desafio.spring.repositories.PurchaseRepository;
import com.desafio.spring.repositories.UserRepository;
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
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PurchaseRequestServiceImplTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private PurchaseRequestDto request;

    @Autowired
    @InjectMocks
    private PurchaseRequestServiceImpl service;

    @Mock
    ProductRepository repository;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws IOException {
        request = OBJECT_MAPPER.readValue(
                new File("src/main/resources/test/purchase_jsons/purchase_request.json"),
                new TypeReference<PurchaseRequestDto>() {});
    }

    @Test
    void shouldProcessThePurchaseRequest() throws Exception {
        //Given
        final ProductDto p1 = new ProductDto(
                1, "Desmalezadora", "Herramientas", "Makita",
                9600, 5, "SI", "****");
        final ProductDto p2 = new ProductDto(
                4, "Zapatillas Deportivas", "Deportes", "Nike",
                14000, 4, "SI", "*****");

        //When
        when(userRepository.exists(any())).thenReturn(true);
        when(repository.findProductById(1)).thenReturn(p1);
        when(repository.findProductById(4)).thenReturn(p2);

        PurchaseReqResponseDto result = service.process(request);

        //Then
        assertEquals(23600, result.getTicket().getTotal());
        assertEquals(HttpStatus.OK, result.getStatusCode().getStatus());
    }

    @Test
    void shouldReturnBadUserException() throws Exception {
        //Given
        boolean thrown = false;

        //When
        when(userRepository.exists(any())).thenReturn(false);
        try {
            service.process(request);
        } catch (BadUserException e) {
            thrown = true;
        }

        //That
        assertTrue(thrown);
    }

    @Test
    void shouldThrowIllegalArgExceptionWhenProductsAttributesAreNotOk() throws Exception {
        //Given
        boolean thrown = false;

        final ProductDto p1 = new ProductDto(
                1, "Desmalezadora", "Herramientas", "Makita",
                9600, 5, "SI", "****");
        final ProductDto p2 = new ProductDto(
                4, "Zapatillas Deportivas", "Deportes", "Adidas",
                13650, 6, "SI", "*****");

        //When
        when(userRepository.exists(any())).thenReturn(true);
        when(repository.findProductById(1)).thenReturn(p1);
        when(repository.findProductById(4)).thenReturn(p2);

        try {
            service.process(request);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        //That
        assertTrue(thrown);
    }

    @Test
    void shouldThrowNoAvailableStockException() throws Exception {
        //Given
        boolean thrown = false;

        final ProductDto p1 = new ProductDto(
                1, "Desmalezadora", "Herramientas", "Makita",
                9600, 5, "SI", "****");
        final ProductDto p2 = new ProductDto(
                4, "Zapatillas Deportivas", "Deportes", "Nike",
                14000, 0, "SI", "*****");

        //When
        when(userRepository.exists(any())).thenReturn(true);
        when(repository.findProductById(1)).thenReturn(p1);
        when(repository.findProductById(4)).thenReturn(p2);

        try {
            service.process(request);
        } catch (NoAvailableStockException e) {
            thrown = true;
        }

        //Then
        assertTrue(thrown);
        verifyNoInteractions(purchaseRepository);
    }

}