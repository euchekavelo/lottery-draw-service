package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.exception.TicketNotFoundException;
import ru.mephi.lotterydrawservice.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}/check-result")
    public ResponseEntity<TicketResponseDto> checkTicketResult(@PathVariable long id) throws TicketNotFoundException {
        return ResponseEntity.ok(ticketService.checkTicketResult(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> checkTicketResults() {
        return ResponseEntity.ok(ticketService.checkTicketResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable long id) throws TicketNotFoundException {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
}
