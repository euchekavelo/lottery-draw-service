package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;
import ru.mephi.lotterydrawservice.model.Invoice;
import ru.mephi.lotterydrawservice.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {
    private long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentTime;
    private Invoice invoice;
}
