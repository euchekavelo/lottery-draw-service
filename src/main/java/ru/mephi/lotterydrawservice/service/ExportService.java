package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.PeriodRequestDto;
import ru.mephi.lotterydrawservice.dto.request.WinningRequestDto;
import ru.mephi.lotterydrawservice.dto.response.*;

public interface ExportService {

    ResponseDto creditWinnings(WinningRequestDto winningRequestDto);

    DrawWinnersResponseDto getInformationAboutWinnersOfDraw(long drawId);

    UserStatisticsResponseDto getUserStatisticsForAPeriod(PeriodRequestDto periodRequestDto);

    WinningStatisticsResponseDto getWinningStatisticsForAPeriod(PeriodRequestDto periodRequestDto);

    DrawStatisticsResponseDto getDrawStatisticsForAPeriod(PeriodRequestDto periodRequestDto);
}
