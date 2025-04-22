package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;

import java.time.LocalDateTime;

@Data
public class DrawResponseDto {

    private long id;
    private LotteryType lotteryType;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private DrawStatus status;
}
