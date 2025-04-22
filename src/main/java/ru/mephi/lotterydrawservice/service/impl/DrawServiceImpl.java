package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.DrawResult;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.repository.DrawResultRepository;
import ru.mephi.lotterydrawservice.service.DrawService;

import java.util.Optional;

@Service
public class DrawServiceImpl implements DrawService {

    private final DrawResultRepository drawResultRepository;
    private final DrawRepository drawRepository;

    @Autowired
    public DrawServiceImpl(DrawResultRepository drawResultRepository, DrawRepository drawRepository) {
        this.drawResultRepository = drawResultRepository;
        this.drawRepository = drawRepository;
    }

    @Override
    public WinningCombinationResponseDto getWinningCombinationOfTheDraw(long drawId) {
        DrawResult drawResult = drawResultRepository.findById(drawId)
                .orElseThrow(() -> new DrawResultNotFoundException("The draw result for the specified draw was not found."));

        return WinningCombinationResponseDto.builder()
                .winningCombination(drawResult.getWinningCombination())
                .build();
    }

    @Override
    public Boolean checkDrawExistsAndActive(long drawId) {
        Optional<Draw> draw = drawRepository.findById(drawId);
        return draw.map(value -> value.getStatus().equals(DrawStatus.ACTIVE)).orElse(false);
    }
}
