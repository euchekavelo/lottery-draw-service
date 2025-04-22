package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;

public interface DrawService {

    WinningCombinationResponseDto getWinningCombinationOfTheDraw(long drawId) throws DrawResultNotFoundException;

    Boolean checkDrawExistsAndActive(long drawId);
}
