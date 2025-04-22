package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.PaymentRequestDto;
import ru.mephi.lotterydrawservice.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto pay(PaymentRequestDto dto);
}
