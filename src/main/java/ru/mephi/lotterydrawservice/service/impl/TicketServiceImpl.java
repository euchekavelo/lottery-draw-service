package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.mapper.TicketMapper;
import ru.mephi.lotterydrawservice.model.Ticket;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.repository.TicketRepository;
import ru.mephi.lotterydrawservice.service.TicketService;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketResponseDto checkTicketResult(long ticketId) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with the specified ID not found."));

        return ticketMapper.ticketToTicketResponseDto(ticket);
    }

    @Override
    public List<TicketResponseDto> checkTicketResults() {
        User mockUser = new User();

        return ticketMapper.ticketsToTicketResponseDtoList(mockUser.getTicketList());
    }
}
