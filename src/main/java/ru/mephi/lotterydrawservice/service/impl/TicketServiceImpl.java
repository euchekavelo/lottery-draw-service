package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.mapper.TicketMapper;
import ru.mephi.lotterydrawservice.model.Ticket;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.repository.TicketRepository;
import ru.mephi.lotterydrawservice.security.AuthUser;
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

    private User getAuthUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return authUser.getUser();
    }

    @Override
    public void createTicket(String ticketData, User user) {
        // logic
    }
}
