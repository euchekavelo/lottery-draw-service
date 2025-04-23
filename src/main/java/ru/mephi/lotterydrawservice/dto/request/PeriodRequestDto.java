package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PeriodRequestDto {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
