package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WinningStatisticsResponseDto {

    private int numberWinnings;
    private BigDecimal totalAmount;
}
