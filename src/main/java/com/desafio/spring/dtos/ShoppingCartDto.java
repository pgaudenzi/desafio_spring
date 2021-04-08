package com.desafio.spring.dtos;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDto {

    private static ShoppingCartDto shoppingCart = null;

    private double total;
    private List<PurchaseReqResponseDto> purchases;

    private ShoppingCartDto() {
        purchases = new ArrayList<>();
    }

    public static ShoppingCartDto getInstance() {
        if(shoppingCart == null) {
            shoppingCart = new ShoppingCartDto();
        }
        return shoppingCart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<PurchaseReqResponseDto> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseReqResponseDto> purchases) {
        this.purchases = purchases;
    }
}
