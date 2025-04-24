package ru.mephi.lotterydrawservice.projection;

import java.math.BigDecimal;

public interface WinningStatisticProjection {

    int getNumberWinnings();

    BigDecimal getTotalAmount();
}
