package com.desafio.spring.services;

import com.desafio.spring.dtos.*;
import com.desafio.spring.exceptions.BadUserException;
import com.desafio.spring.exceptions.NoAvailableStockException;
import com.desafio.spring.exceptions.ProductNotFoundException;
import com.desafio.spring.repositories.ProductRepository;
import com.desafio.spring.repositories.PurchaseRepository;
import com.desafio.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service to manage a purchase request
 */
@Service
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final AtomicInteger id = new AtomicInteger();

    @Autowired
    ProductRepository repository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Process a purchase request
     * @param request purchase request
     * @return the response to the purchase request
     * @throws ProductNotFoundException if the product does not exists
     * @throws NoAvailableStockException if the stock is not enough to process the purchase
     */
    @Override
    public PurchaseReqResponseDto process(PurchaseRequestDto request)
            throws ProductNotFoundException, NoAvailableStockException, BadUserException {

        if (userRepository.exists(request.getUsername())) {
            StatusCodeDto status;
            TicketDto ticket = calculateTicket(request);
            status = new StatusCodeDto(HttpStatus.OK, "La solicitud de compra se " +
                    "completo con exito");
            PurchaseReqResponseDto processedPurchase = new PurchaseReqResponseDto(ticket, status);
            purchaseRepository.addPurchase(request.getUsername(), processedPurchase);
            return processedPurchase;
        } else {
            throw new BadUserException();
        }
    }

    /**
     * Get the user's purchases
     * @param username
     * @return the purchases requested during the user session
     * @throws BadUserException if the user does not exists
     */
    @Override
    public UserShoppingCartDto getUserShoppingCart(String username) throws BadUserException {
        if (userRepository.exists(username)) {
            final List<PurchaseReqResponseDto> userPurchases = purchaseRepository.getUserPurchases(username);
            double total = 0.0;

            for (PurchaseReqResponseDto purchase : userPurchases) {
                total += purchase.getTicket().getTotal();
            }

            return new UserShoppingCartDto(total, userPurchases);
        } else {
            throw new BadUserException();
        }
    }

    /**
     * @param request
     * @return the ticker with the articles
     * @throws NoAvailableStockException
     * @throws ProductNotFoundException
     */
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


    /**
     * Aux method to validate that the values of the products of the purchase request are correct
     * @param products
     * @param articles
     * @throws NoAvailableStockException
     */
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
