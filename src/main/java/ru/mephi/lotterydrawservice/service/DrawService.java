package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;

public interface DrawService {

    WinningCombinationResponseDto getWinningCombinationOfTheDraw(long drawId);

    Boolean checkDrawExistsAndActive(long drawId);
}
