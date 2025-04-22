package ru.mephi.lotterydrawservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mephi.lotterydrawservice.dto.request.PaymentRequestDto;
import ru.mephi.lotterydrawservice.dto.response.PaymentResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;

public interface PaymentService {

    PaymentResponseDto pay(PaymentRequestDto dto);
}
