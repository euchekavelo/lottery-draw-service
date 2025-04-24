package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WinningRequestDto {

    private long drawId;
    private BigDecimal winningAmount;
}
