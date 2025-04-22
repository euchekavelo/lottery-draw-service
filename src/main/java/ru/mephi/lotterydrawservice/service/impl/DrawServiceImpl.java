package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.dto.DrawDto;
import ru.mephi.lotterydrawservice.dto.request.DrawCreateRequestDto;
import ru.mephi.lotterydrawservice.dto.response.DrawCreateResponseDto;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.mapper.DrawMapper;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.DrawResult;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.repository.DrawResultRepository;
import ru.mephi.lotterydrawservice.service.DrawResultService;
import ru.mephi.lotterydrawservice.service.DrawService;
import ru.mephi.lotterydrawservice.service.InvoiceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Service
public class DrawServiceImpl implements DrawService {

    private final DrawResultRepository drawResultRepository;
    private final DrawMapper drawMapper;
    private final DrawRepository drawRepository;
    private final InvoiceService invoiceService;

    @Autowired
    public DrawServiceImpl(DrawResultRepository drawResultRepository,
                           DrawMapper drawMapper, DrawRepository drawRepository, DrawResultService drawResultService, InvoiceService invoiceService) {
        this.drawResultRepository = drawResultRepository;
        this.drawRepository = drawRepository;
        this.drawMapper = drawMapper;
        this.invoiceService = invoiceService;
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
    public DrawCreateResponseDto createDraw(DrawCreateRequestDto createRequestDto) {
        validateDrawCreateRequestDto(createRequestDto);
        Draw draw = drawRepository.save(drawMapper.toDraw(createRequestDto, DrawStatus.PLANNED));
        return drawMapper.toDrawCreateResponseDto(draw);
    }

    @Override
    public List<DrawDto> getDrawByStatus(DrawStatus status) {
        return drawRepository.findAllByStatus(status).stream()
                .map(drawMapper::toDrawDto)
                .toList();
    }

    @Transactional
    @Override
    public void cancelDraw(Long id) {
        drawRepository.findById(id)
                .ifPresentOrElse(draw -> {
                    try {
                        draw.setStatus(DrawStatus.CANCELLED);
                        invoiceService.cancelByDraw(draw.getId());
                        draw.getTicketList()
                                .forEach(ticket -> ticket.setStatus(TicketStatus.LOSE));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, () -> {
                    throw new NoSuchElementException("Draw was not found");
                });
    }

    private void validateDrawCreateRequestDto(DrawCreateRequestDto createRequestDto) {
        if (!createRequestDto.getLotteryType().equals(LotteryType.AUTO.toString()) &&
                !createRequestDto.getLotteryType().equals(LotteryType.FIVE_OUT_OF_THIRTY_SIX.toString())) {
            throw new IllegalArgumentException("Invalid lotteryType");
        }
        if (isNull(createRequestDto.getStartTime()) ||
                createRequestDto.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid startTime");
        }
        if (isNull(createRequestDto.getFinishTime()) ||
                createRequestDto.getFinishTime().isBefore(LocalDateTime.now()) ||
                createRequestDto.getFinishTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid finishTime");
        }
    }
}
