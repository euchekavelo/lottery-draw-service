package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DrawCreateRequestDto {
    private String lotteryType;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
}
