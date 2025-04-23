package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WinnerResponseDto {

    private long ticketId;
    private BigDecimal amount;
}
