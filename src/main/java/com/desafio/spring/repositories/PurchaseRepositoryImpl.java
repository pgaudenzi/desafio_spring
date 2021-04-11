package com.desafio.spring.repositories;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private final Map<String, List<PurchaseReqResponseDto>> purchases = new HashMap<>();

    @Override
    public List<PurchaseReqResponseDto> getUserPurchases(String username) {
        if (purchases.containsKey(username)) return purchases.get(username);
        throw new IllegalArgumentException("There are not purchases for the user " + username + " yet");
    }

    @Override
    public void addPurchase(String username, PurchaseReqResponseDto request) {
        List<PurchaseReqResponseDto> userPurchases;
        if (!purchases.containsKey(username)) {
            userPurchases = new ArrayList<>();
        } else {
            userPurchases = purchases.get(username);
        }
        userPurchases.add(request);
        purchases.put(username, userPurchases);
    }

}
