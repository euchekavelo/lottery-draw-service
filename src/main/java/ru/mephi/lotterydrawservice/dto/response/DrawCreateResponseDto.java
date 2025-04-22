package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;

import java.time.LocalDateTime;

@Data
public class DrawCreateResponseDto {
    private Long id;
    private LotteryType lotteryType;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
}
