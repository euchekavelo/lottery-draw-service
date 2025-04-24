package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.lotterydrawservice.dto.request.PaymentRequestDto;
import ru.mephi.lotterydrawservice.dto.response.PaymentResponseDto;
import ru.mephi.lotterydrawservice.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<PaymentResponseDto> pay(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(paymentService.pay(paymentRequestDto));
    }
}
