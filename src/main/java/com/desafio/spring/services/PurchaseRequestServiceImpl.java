package com.desafio.spring.services;

import com.desafio.spring.dtos.*;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.exceptions.ProductNotFoundException;
import com.desafio.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        final List<ProductDto> products = new ArrayList<>();
        TicketDto ticket;
        double total = 0.0;

        // Get products by ID
        for (ItemTicketDto article : articles) {
            products.add(repository.findProductById(article.getProductId()));
        }

        // Validate products
        validateProducts(products, articles);

        // If the validation went well, then I process the purchase
        for (int i = 0; i < articles.size(); i++) {
            total += articles.get(i).getQuantity() * products.get(i).getPrice();
            products.get(i).setQuantity(products.get(i).getQuantity() - articles.get(i).getQuantity());
        }

        ticket = new TicketDto(id.incrementAndGet(), request.getArticles(), total);
        return ticket;
    }


    private void validateProducts(List<ProductDto> products, List<ItemTicketDto> articles) throws NoAvailableStockException {
        for (int i = 0; i < articles.size(); i++) {
            if (!products.get(i).getName().equals(articles.get(i).getName())
                    || !products.get(i).getBrand().equals(articles.get(i).getBrand())) {
                throw new IllegalArgumentException("Los datos del producto enviado en la solicitud - ID:"
                        + articles.get(i).getProductId() + ", nombre: " + articles.get(i).getName()
                        + ", marca: " + articles.get(i).getBrand()
                        + " - son incorrectos");
            }

            if (articles.get(i).getQuantity() > products.get(i).getQuantity()) {
                throw new NoAvailableStockException(products.get(i));
            }
        }
    }
}
