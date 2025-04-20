package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.model.User;

import java.util.List;

public interface TicketService {

    TicketResponseDto checkTicketResult(long ticketId) throws TicketNotFoundException;

    List<TicketResponseDto> checkTicketResults();

    void createTicket(String ticketData, User user);
}
