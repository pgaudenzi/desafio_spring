package com.desafio.spring.repositories;

import com.desafio.spring.dtos.PurchaseReqResponseDto;

import java.util.List;

public interface PurchaseRepository {

    List<PurchaseReqResponseDto> getUserPurchases(String username);

    void addPurchase(String username, PurchaseReqResponseDto request);

}
