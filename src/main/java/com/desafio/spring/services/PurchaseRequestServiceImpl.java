package com.desafio.spring.services;

import com.desafio.spring.dtos.*;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.exceptions.ProductNotFoundException;
import com.desafio.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final AtomicInteger id = new AtomicInteger();

    @Autowired
    ProductRepository repository;

    @Override
    public PurchaseReqResponseDto process(PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException {

        StatusCodeDto status;
        TicketDto ticket = calculateTicket(request);
        status = new StatusCodeDto(HttpStatus.OK, "La solicitud de compra se " +
                "completo con exito");
        return new PurchaseReqResponseDto(ticket, status);
    }

    @Override
    public ShoppingCartDto processShoppingCart(PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException {

        final PurchaseReqResponseDto purchase = process(request);
        final double purchaseTotal = purchase.getTicket().getTotal();
        final ShoppingCartDto shoppingCart = ShoppingCartDto.getInstance();
        final List<PurchaseReqResponseDto> purchases = shoppingCart.getPurchases();

        purchases.add(purchase);
        shoppingCart.setTotal(shoppingCart.getTotal() + purchaseTotal);
        shoppingCart.setPurchases(purchases);

        return shoppingCart;
    }

    private TicketDto calculateTicket(PurchaseRequestDto request)
            throws NoAvailableStockException, ProductNotFoundException {

        final List<ItemTicketDto> articles = request.getArticles();
        TicketDto ticket;
        double total = 0.0;
        for (ItemTicketDto article : articles) {
            ProductDto product = repository.findProductById(article.getProductId());

            if (article.getQuantity() > product.getQuantity()) {
                throw new NoAvailableStockException(product);
            }

            total += article.getQuantity() * product.getPrice();
            product.setQuantity(product.getQuantity() - article.getQuantity());
        }

        ticket = new TicketDto(id.incrementAndGet(), request.getArticles(), total);
        return ticket;
    }

}
