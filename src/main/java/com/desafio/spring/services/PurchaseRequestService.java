package com.desafio.spring.services;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;
import com.desafio.spring.dtos.ShoppingCartDto;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.exceptions.ProductNotFoundException;

public interface PurchaseRequestService {

    PurchaseReqResponseDto process(PurchaseRequestDto request) throws ProductNotFoundException, NoAvailableStockException;

    ShoppingCartDto processShoppingCart(PurchaseRequestDto request) throws ProductNotFoundException, NoAvailableStockException;

}
