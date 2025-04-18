package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.model.DrawResult;
import ru.mephi.lotterydrawservice.repository.DrawResultRepository;
import ru.mephi.lotterydrawservice.service.DrawService;

@Service
public class DrawServiceImpl implements DrawService {

    private final DrawResultRepository drawResultRepository;

    @Autowired
    public DrawServiceImpl(DrawResultRepository drawResultRepository) {
        this.drawResultRepository = drawResultRepository;
    }

    @Override
    public WinningCombinationResponseDto getWinningCombinationOfTheDraw(long drawId) throws DrawResultNotFoundException {
        DrawResult drawResult = drawResultRepository.findById(drawId)
                .orElseThrow(() -> new DrawResultNotFoundException("The draw result for the specified draw was not found."));

        return WinningCombinationResponseDto.builder()
                .winningCombination(drawResult.getWinningCombination())
                .build();
    }
}
