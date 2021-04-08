package com.desafio.spring.controllers;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;
import com.desafio.spring.dtos.ShoppingCartDto;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.exceptions.ProductNotFoundException;
import com.desafio.spring.services.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class PurchaseRequestController {

    @Autowired
    PurchaseRequestService service;

    /**
     * Endpoint to post a new purchase request
     * @param request
     * @return
     * @throws ProductNotFoundException
     * @throws NoAvailableStockException
     */
    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseReqResponseDto> purchaseRequest(@RequestBody PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException {
        return new ResponseEntity<>(service.process(request), HttpStatus.OK);
    }

    /**
     * Endpoint to post purchase requests in a shopping cart
     * @param request
     * @return
     * @throws ProductNotFoundException
     * @throws NoAvailableStockException
     */
    @PostMapping("/shopping-cart")
    public ResponseEntity<ShoppingCartDto> shoppingCartRequest(@RequestBody PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException {
        return new ResponseEntity<>(service.processShoppingCart(request), HttpStatus.OK);
    }

}
