package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {
    private long invoiceId;
    private String cardNumber;
    private String cvc;
    private BigDecimal amount;
}
