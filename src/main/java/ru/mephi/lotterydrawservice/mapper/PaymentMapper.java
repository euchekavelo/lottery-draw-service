package ru.mephi.lotterydrawservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mephi.lotterydrawservice.dto.response.PaymentResponseDto;
import ru.mephi.lotterydrawservice.model.Payment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);
}
