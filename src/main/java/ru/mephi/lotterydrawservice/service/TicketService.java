package ru.mephi.lotterydrawservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.model.User;

import java.util.List;

public interface TicketService {

    TicketResponseDto checkTicketResult(long ticketId) throws TicketNotFoundException;

    List<TicketResponseDto> checkTicketResults();

    TicketResponseDto getTicketById(long id) throws TicketNotFoundException;

    void createTicket(User user, String ticketData) throws JsonProcessingException, DrawResultNotFoundException;
}
