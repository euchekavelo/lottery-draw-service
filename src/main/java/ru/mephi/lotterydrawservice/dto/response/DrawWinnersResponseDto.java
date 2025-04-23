package ru.mephi.lotterydrawservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DrawWinnersResponseDto {

    private long drawId;
    private String winningCombination;
    private List<WinnerResponseDto> winners;
}
