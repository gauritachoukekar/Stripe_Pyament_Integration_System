package com.hulkhiretech.payments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.pojo.PaymentRequest;
import com.hulkhiretech.payments.service.interfaces.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    
    @PostMapping
    public String createPayment(
            @Valid @RequestBody 
            PaymentRequest paymentRequest) {
        log.info("Creating payment... paymentRequest: {}", paymentRequest);
        
        String serviceResponse = paymentService
        		.validateAndCreatePayment(paymentRequest);
        
        log.info("Payment creation response: {}", serviceResponse);
		return serviceResponse;
    }

}