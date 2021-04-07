package com.desafio.spring.services;

import com.desafio.spring.dtos.PurchaseReqResponseDto;
import com.desafio.spring.dtos.PurchaseRequestDto;

public interface PurchaseRequestService {

    PurchaseReqResponseDto process(PurchaseRequestDto request);

}
