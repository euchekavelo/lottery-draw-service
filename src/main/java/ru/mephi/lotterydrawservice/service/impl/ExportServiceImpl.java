package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.dto.request.PeriodRequestDto;
import ru.mephi.lotterydrawservice.dto.request.WinningRequestDto;
import ru.mephi.lotterydrawservice.dto.response.*;
import ru.mephi.lotterydrawservice.exception.DrawNotFoundException;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.model.*;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;
import ru.mephi.lotterydrawservice.projection.UserStatisticProjection;
import ru.mephi.lotterydrawservice.projection.WinningStatisticProjection;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.repository.TicketRepository;
import ru.mephi.lotterydrawservice.repository.UserRepository;
import ru.mephi.lotterydrawservice.repository.WinningRepository;
import ru.mephi.lotterydrawservice.service.EmailService;
import ru.mephi.lotterydrawservice.service.ExportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    private final DrawRepository drawRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final WinningRepository winningRepository;
    private final EmailService emailService;

    @Autowired
    public ExportServiceImpl(DrawRepository drawRepository, TicketRepository ticketRepository,
                             UserRepository userRepository, WinningRepository winningRepository, EmailService emailService) {

        this.drawRepository = drawRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.winningRepository = winningRepository;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public ResponseDto creditWinnings(WinningRequestDto winningRequestDto) {
        Draw draw = drawRepository.findById(winningRequestDto.getDrawId())
                .orElseThrow(() -> new DrawNotFoundException("Lottery draw with the specified ID was not found."));

        if (!draw.getStatus().equals(DrawStatus.COMPLETED)) {
            throw new UnsupportedOperationException("Payout of winnings is possible only for lottery draws with "
                    + DrawStatus.COMPLETED + " status.");
        }

        List<Ticket> ticketList = ticketRepository.findAllByDrawAndStatus(draw, TicketStatus.WIN);
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("There are no winning tickets identified for this lottery draw.");
        }

        BigDecimal winningAmount = winningRequestDto.getWinningAmount()
                .divide(BigDecimal.valueOf(ticketList.size()), 2, RoundingMode.HALF_UP);

        List<Ticket> winningTickets = ticketList.stream()
                .filter(ticket -> ticket.getWinning() == null)
                .toList();

        winningTickets.forEach(ticket -> {
            addWinningToTicket(winningAmount, ticket);
            increaseUserBalance(winningAmount, ticket.getUser());
        });

        List<Ticket> savedTickets = ticketRepository.saveAll(winningTickets);
        sendEmailsToTheWinners(savedTickets);

        return ResponseDto.builder()
                .message("The winning fund was distributed among the winners who had not been credited earlier.")
                .result(true)
                .build();
    }

    @Override
    public DrawWinnersResponseDto getInformationAboutWinnersOfDraw(long drawId) {
        Draw draw = drawRepository.findById(drawId)
                .orElseThrow(() -> new DrawNotFoundException("Lottery draw with the specified ID was not found."));

        String winningCombination = draw.getDrawResult().getWinningCombination();
        List<Ticket> ticketList = ticketRepository.findAllByDrawAndStatus(draw, TicketStatus.WIN);

        List<WinnerResponseDto> winnerResponseDtoList = ticketList.stream()
                .map(ticket -> {
                    WinnerResponseDto winnerResponseDto = new WinnerResponseDto();
                    winnerResponseDto.setTicketId(ticket.getId());
                    winnerResponseDto.setAmount(ticket.getWinning().getAmount());

                    return winnerResponseDto;
                })
                .toList();

        return DrawWinnersResponseDto.builder()
                .drawId(draw.getId())
                .winningCombination(winningCombination)
                .winners(winnerResponseDtoList)
                .build();
    }

    @Override
    public UserStatisticsResponseDto getUserStatisticsForAPeriod(PeriodRequestDto periodRequestDto) {
        if (!isValidDatePeriod(periodRequestDto)) {
            throw new IllegalArgumentException("Dates must be specified, and fromDate must be less than toDate.");
        }

        List<UserStatisticProjection> userStatisticProjections =
                userRepository.getUserStatisticsByPeriodBetween(periodRequestDto.getFromDate(), periodRequestDto.getToDate());

        List<UserStatisticResponseDto> userStatisticResponseDtoList = userStatisticProjections.stream()
                .map(userStatisticProjection -> {
                    UserStatisticResponseDto userStatisticResponseDto = new UserStatisticResponseDto();
                    userStatisticResponseDto.setUserId(userStatisticProjection.getUserId());
                    userStatisticResponseDto.setTotalWinnings(userStatisticProjection.getTotalWinnings());
                    userStatisticResponseDto.setWinningTicketsCount(userStatisticProjection.getWinningTicketsCount());

                    return userStatisticResponseDto;
                })
                .toList();

        return UserStatisticsResponseDto.builder()
                .statistics(userStatisticResponseDtoList)
                .build();
    }

    @Override
    public WinningStatisticsResponseDto getWinningStatisticsForAPeriod(PeriodRequestDto periodRequestDto) {
        if (!isValidDatePeriod(periodRequestDto)) {
            throw new IllegalArgumentException("Dates must be specified, and fromDate must be less than toDate.");
        }

        WinningStatisticProjection winningStatisticProjection =
                winningRepository.getWinningStatisticsByPeriodBetween(periodRequestDto.getFromDate(),
                                periodRequestDto.getToDate()).orElse(null);

        WinningStatisticsResponseDto winningStatisticsResponseDto = new WinningStatisticsResponseDto();
        if (winningStatisticProjection != null) {
            winningStatisticsResponseDto.setNumberWinnings(winningStatisticProjection.getNumberWinnings());
            winningStatisticsResponseDto.setTotalAmount(winningStatisticProjection.getTotalAmount());
        }

        return winningStatisticsResponseDto;
    }

    @Override
    public DrawStatisticsResponseDto getDrawStatisticsForAPeriod(PeriodRequestDto periodRequestDto) {
        if (!isValidDatePeriod(periodRequestDto)) {
            throw new IllegalArgumentException("Dates must be specified, and fromDate must be less than toDate.");
        }

        int countDraw = drawRepository.getCountAllByPeriodBetween(periodRequestDto.getFromDate(),
                periodRequestDto.getToDate());

        return DrawStatisticsResponseDto.builder()
                .drawCount(countDraw)
                .build();
    }

    private void addWinningToTicket(BigDecimal winningAmount, Ticket ticket) {
        Winning winning = new Winning();
        winning.setAmount(winningAmount);
        winning.setTicket(ticket);
        ticket.setWinning(winning);
    }

    private void increaseUserBalance(BigDecimal winningAmount, User user) {
        Balance balance = user.getBalance();
        balance.setAmount(balance.getAmount().add(winningAmount));
    }

    private void sendEmailsToTheWinners(List<Ticket> ticketList) {
        ticketList.forEach(ticket -> {
            StringBuilder stringBuilder = new StringBuilder("Congratulations! You have won ");
            stringBuilder.append(ticket.getWinning().getAmount());
            stringBuilder.append(" in the draw with ID ");
            stringBuilder.append(ticket.getDraw().getId());
            stringBuilder.append(".");

            emailService.sendEmail(ticket.getUser().getLogin(), "Winning a lottery draw",
                    stringBuilder.toString());
        });
    }

    private boolean isValidDatePeriod(PeriodRequestDto periodRequestDto) {
        LocalDateTime fromDate = periodRequestDto.getFromDate();
        LocalDateTime toDate = periodRequestDto.getToDate();

        return (fromDate != null && toDate != null) && fromDate.isBefore(toDate);
    }
}
