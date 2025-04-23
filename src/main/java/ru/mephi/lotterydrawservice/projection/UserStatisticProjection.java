package ru.mephi.lotterydrawservice.projection;

import java.math.BigDecimal;

public interface UserStatisticProjection {

    long getUserId();

    BigDecimal getTotalWinnings();

    int getWinningTicketsCount();
}
