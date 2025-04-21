package ru.mephi.lotterydrawservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.mapper.TicketMapper;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.Ticket;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.repository.TicketRepository;
import ru.mephi.lotterydrawservice.security.AuthUser;
import ru.mephi.lotterydrawservice.service.TicketService;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final DrawRepository drawRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, DrawRepository drawRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.drawRepository = drawRepository;
    }

    @Override
    public TicketResponseDto checkTicketResult(long ticketId) throws TicketNotFoundException {
        User currentuser = getAuthUser();
        Ticket ticket = ticketRepository.findByIdAndUser(ticketId, currentuser)
                .orElseThrow(() -> new TicketNotFoundException("No ticket with the specified ID was found " +
                        "for the current user."));

        return ticketMapper.ticketToTicketResponseDto(ticket);
    }

    @Override
    public List<TicketResponseDto> checkTicketResults() {
        User currentuser = getAuthUser();

        return ticketMapper.ticketsToTicketResponseDtoList(currentuser.getTicketList());
    }

    @Override
    public TicketResponseDto getTicketById(long id) throws TicketNotFoundException {
        User currentuser = getAuthUser();
        Ticket ticket = ticketRepository.findByIdAndUser(id, currentuser)
                .orElseThrow(() -> new TicketNotFoundException("No ticket with the specified ID was found " +
                        "for the current user."));

        return ticketMapper.ticketToTicketResponseDto(ticket);
    }

    @Override
    public void createTicket(User user, String ticketData) throws JsonProcessingException, DrawResultNotFoundException {
        long drawId = parseData(ticketData).get("drawID").asLong();
        String combinationNumbers = parseData(ticketData).get("numbers").asText();

        Draw draw = drawRepository.findById(drawId)
                .orElseThrow(() -> new DrawResultNotFoundException("Draw not found"));
        if (draw.getStatus() != DrawStatus.ACTIVE) {
            throw new IllegalArgumentException("Draw is not active");
        }
        if(draw.getLotteryType().equals(LotteryType.FIVE_OUT_OF_THIRTY_SIX)){
            validateTicketNumbers(combinationNumbers);
        } else {
            combinationNumbers=generateRandomNumbers();
        }

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setDraw(draw);
        ticket.setData(combinationNumbers);
        ticket.setStatus(TicketStatus.PENDING);
        ticketRepository.save(ticket);
    }

    private User getAuthUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return authUser.getUser();
    }

    private JsonNode parseData(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    private String generateRandomNumbers() {
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();

        while (uniqueNumbers.size() < 5) {
            int randomNumber = random.nextInt(99) + 1;
            uniqueNumbers.add(randomNumber);
        }

        List<Integer> numbersList = new ArrayList<>(uniqueNumbers);
        Collections.sort(numbersList);

        return numbersList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }


    private void validateTicketNumbers(String data) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле не может быть пустым.");
        }

        List<Integer> numbers;

        try {
            numbers = Arrays.stream(data.trim().split(" "))
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверные данные. Должны быть введены цифры через пробел.");
        }

        if (numbers.size() != 5) {
            throw new IllegalArgumentException("Для лотерея '5 out of 36' должно быть введено 5 цифр.");
        }

        boolean isValidRange = numbers.stream().allMatch(num -> num >= 1 && num <= 36);
        if (!isValidRange) {
            throw new IllegalArgumentException("Все числа должны быть в диапозоне от 1 до 36");
        }

        if (numbers.stream().distinct().count() != numbers.size()) {
            throw new IllegalArgumentException("Все числа должны быть уникальными.");
        }
    }
}
