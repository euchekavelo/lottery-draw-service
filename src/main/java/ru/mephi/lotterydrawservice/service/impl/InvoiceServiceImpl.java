package ru.mephi.lotterydrawservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.dto.request.InvoiceRequestDto;
import ru.mephi.lotterydrawservice.dto.request.TicketDataRequestDto;
import ru.mephi.lotterydrawservice.dto.response.InvoiceResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawNotFoundException;
import ru.mephi.lotterydrawservice.exception.InvalidTicketDataException;
import ru.mephi.lotterydrawservice.exception.InvoiceNotFoundException;
import ru.mephi.lotterydrawservice.mapper.InvoiceMapper;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.Invoice;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.InvoiceStatus;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.repository.InvoiceRepository;
import ru.mephi.lotterydrawservice.service.AuthService;
import ru.mephi.lotterydrawservice.service.InvoiceService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final DrawRepository drawRepository;
    private final InvoiceMapper invoiceMapper;
    private final ObjectMapper objectMapper;
    private final AuthService authService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, DrawRepository drawRepository,
                              InvoiceMapper invoiceMapper, ObjectMapper objectMapper, AuthService authService) {

        this.invoiceRepository = invoiceRepository;
        this.drawRepository = drawRepository;
        this.invoiceMapper = invoiceMapper;
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public InvoiceResponseDto register(InvoiceRequestDto invoiceRequestDto) {
        User user = authService.getAuthUser();

        TicketDataRequestDto ticketData = invoiceRequestDto.getTicketData();
        String ticketDataString;

        try {
            ticketDataString = objectMapper.writeValueAsString(invoiceRequestDto.getTicketData());
        } catch (JsonProcessingException err) {
            throw new InvalidTicketDataException("Invalid ticketData format.");
        }

        if (!Objects.equals(user.getId(), ticketData.getUserId())) {
            throw new SecurityException("Authenticated user does not match ticket owner.");
        }

        if (!checkDrawExistsAndActive(ticketData.getDrawId())) {
            throw new DrawNotFoundException("The specified draw does not exist or is not active.");
        }

        Invoice invoice = new Invoice();
        invoice.setTicketData(ticketDataString);
        invoice.setStatus(InvoiceStatus.PENDING);

        return invoiceMapper.invoiceToInvoiceResponseDto(invoiceRepository.save(invoice));
    }

    @Transactional
    @Override
    public void cancelByDraw(long drawId) {
        List<Invoice> invoices = invoiceRepository.findAllByDrawId(drawId);
        for (Invoice invoice : invoices) {
            invoice.setStatus(InvoiceStatus.FAILED);
        }
    }

    @Override
    public Invoice findInvoiceById(long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice with ID " + invoiceId + " not found."));
    }

    private Boolean checkDrawExistsAndActive(long drawId) {
        Optional<Draw> draw = drawRepository.findById(drawId);
        return draw.map(value -> value.getStatus().equals(DrawStatus.ACTIVE)).orElse(false);
    }
}
