package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.DrawDto;
import ru.mephi.lotterydrawservice.dto.request.DrawCreateRequestDto;
import ru.mephi.lotterydrawservice.dto.response.DrawCreateResponseDto;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;

import java.util.List;

public interface DrawService {

    WinningCombinationResponseDto getWinningCombinationOfTheDraw(long drawId);

    DrawCreateResponseDto createDraw(DrawCreateRequestDto createRequestDto);


    List<DrawDto> getDrawByStatus(DrawStatus status);

    void cancelDraw(Long id);
}
