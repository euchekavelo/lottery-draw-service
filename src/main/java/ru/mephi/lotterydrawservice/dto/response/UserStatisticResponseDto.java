package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserStatisticResponseDto {

    private long userId;
    private BigDecimal totalWinnings;
    private int winningTicketsCount;
}
