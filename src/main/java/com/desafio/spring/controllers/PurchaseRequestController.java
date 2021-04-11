package com.desafio.spring.controllers;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;
import com.desafio.spring.dtos.UserShoppingCartDto;
import com.desafio.spring.exceptions.BadUserException;
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
     * @param request purchase request
     * @return purchase request response
     * @throws ProductNotFoundException if the product does not exists
     * @throws NoAvailableStockException if the stock is not enough to process the purchase
     */
    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseReqResponseDto> purchaseRequest(@RequestBody PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException, BadUserException {
        return new ResponseEntity<>(service.process(request), HttpStatus.OK);
    }

    /**
     * Endpoint to get the user's shopping cart
     * @param username
     * @return a list of purchases made by the user
     * @throws BadUserException if the user does not exists
     */
    @GetMapping("/shopping-cart")
    public ResponseEntity<UserShoppingCartDto> getUserShoppingCart(@RequestParam String username)
            throws BadUserException {
        return new ResponseEntity<>(service.getUserShoppingCart(username), HttpStatus.OK);
    }

}
