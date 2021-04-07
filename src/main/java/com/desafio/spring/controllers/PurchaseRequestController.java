package com.desafio.spring.controllers;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;
import com.desafio.spring.services.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class PurchaseRequestController {

    @Autowired
    PurchaseRequestService service;

    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseReqResponseDto> purchaseRequest(@RequestBody PurchaseRequestDto request) {
        return new ResponseEntity<>(service.process(request), HttpStatus.OK);
    }

}
