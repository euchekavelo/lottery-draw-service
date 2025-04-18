package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.DrawResult;
import ru.mephi.lotterydrawservice.model.Ticket;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;
import ru.mephi.lotterydrawservice.repository.DrawResultRepository;
import ru.mephi.lotterydrawservice.repository.TicketRepository;
import ru.mephi.lotterydrawservice.service.DrawResultService;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrawResultServiceImpl implements DrawResultService {

    private static final int COUNT_MIN_MATCHES = 3;
    private static final int COUNT_OF_NUMBERS = 5;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final DrawResultRepository drawResultRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public DrawResultServiceImpl(DrawResultRepository drawResultRepository, TicketRepository ticketRepository) {
        this.drawResultRepository = drawResultRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    @Override
    public void defineDrawResults(Draw draw) {
        Map<String, Integer> rangeValuesOfNumbers = setRangeValuesOfNumbers(draw.getLotteryType());
        String winningCombination = getGeneratedWinningCombination(rangeValuesOfNumbers);

        DrawResult drawResult = new DrawResult();
        drawResult.setDraw(draw);
        drawResult.setWinningCombination(winningCombination);

        drawResultRepository.save(drawResult);
        updateTicketStatusesForDraw(draw, winningCombination);
    }

    private void updateTicketStatusesForDraw(Draw draw, String winningCombination) {
        List<Ticket> tickets = draw.getTicketList();
        Set<Integer> winningNumbers = Arrays.stream(winningCombination.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        for (Ticket ticket : tickets) {
            Integer[] commonNumbers = Arrays.stream(ticket.getData().split(" "))
                    .distinct()
                    .map(Integer::parseInt)
                    .filter(winningNumbers::contains)
                    .toArray(Integer[]::new);

            if (draw.getLotteryType().equals(LotteryType.FIVE_OUT_OF_THIRTY_SIX) && commonNumbers.length >= COUNT_MIN_MATCHES) {
                ticket.setStatus(TicketStatus.WIN);
            } else if (draw.getLotteryType().equals(LotteryType.AUTO) && commonNumbers.length == COUNT_OF_NUMBERS) {
                ticket.setStatus(TicketStatus.WIN);
            } else {
                ticket.setStatus(TicketStatus.LOSE);
            }
        }

        ticketRepository.saveAll(tickets);
    }

    private String getGeneratedWinningCombination(Map<String, Integer> rangeValuesOfNumbers) {
        List<Integer> integerList = new ArrayList<>();

        for (int i = 0; i < COUNT_OF_NUMBERS; i++) {
            int randomNumber = generateRandomNumber(rangeValuesOfNumbers);
            while (integerList.contains(randomNumber)) {
                randomNumber = generateRandomNumber(rangeValuesOfNumbers);
            }

            integerList.add(randomNumber);
        }

        return integerList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private int generateRandomNumber(Map<String, Integer> rangeValuesOfNumbers) {
        return SECURE_RANDOM.nextInt(rangeValuesOfNumbers.get("max")
                - rangeValuesOfNumbers.get("min") + 1) + rangeValuesOfNumbers.get("min");
    }

    private Map<String, Integer> setRangeValuesOfNumbers(LotteryType lotteryType) {
        Map<String, Integer> map = new HashMap<>();

        switch (lotteryType) {
            case FIVE_OUT_OF_THIRTY_SIX:
                map.put("min", 1);
                map.put("max", 36);
                break;
            default:
                map.put("min", 1);
                map.put("max", 99);
        }

        return map;
    }
}
