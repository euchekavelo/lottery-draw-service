package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;

import java.util.List;

public interface TicketService {

    TicketResponseDto checkTicketResult(long ticketId) throws TicketNotFoundException;

    List<TicketResponseDto> checkTicketResults();
}
