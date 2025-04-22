package ru.mephi.lotterydrawservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.model.User;

import java.util.List;

public interface TicketService {

    TicketResponseDto checkTicketResult(long ticketId);

    List<TicketResponseDto> checkTicketResults();

    TicketResponseDto getTicketById(long id);

    void createTicket(User user, String ticketData) throws JsonProcessingException;
}
