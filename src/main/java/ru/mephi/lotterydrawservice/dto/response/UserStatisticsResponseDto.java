package ru.mephi.lotterydrawservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserStatisticsResponseDto {

    private List<UserStatisticResponseDto> statistics;
}
