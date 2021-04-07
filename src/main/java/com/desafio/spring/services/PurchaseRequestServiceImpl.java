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
    public PurchaseReqResponseDto process(PurchaseRequestDto request) {
        StatusCodeDto status;
        TicketDto ticket = null;

        try {
            ticket = calculateTicket(request);
            status = new StatusCodeDto(HttpStatus.OK, "La solicitud de compra se " +
                    "completo con exito");
            return new PurchaseReqResponseDto(ticket, status);

        } catch (ProductNotFoundException pnfe) {
            status = new StatusCodeDto(HttpStatus.NOT_FOUND, pnfe.getMessage());
            return new PurchaseReqResponseDto(ticket, status);

        } catch (NoAvailableStockException nase) {
            status = new StatusCodeDto(HttpStatus.INTERNAL_SERVER_ERROR, nase.getMessage());
            return new PurchaseReqResponseDto(ticket, status);
        }
    }

    private TicketDto calculateTicket(PurchaseRequestDto request)
            throws NoAvailableStockException, ProductNotFoundException {

        List<ItemTicketDto> articles = request.getArticles();
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
