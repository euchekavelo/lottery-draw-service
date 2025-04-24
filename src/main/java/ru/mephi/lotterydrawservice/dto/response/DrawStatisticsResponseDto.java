package ru.mephi.lotterydrawservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrawStatisticsResponseDto {

    private int drawCount;
}
