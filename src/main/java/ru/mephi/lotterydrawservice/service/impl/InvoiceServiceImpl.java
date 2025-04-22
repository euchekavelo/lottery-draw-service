package ru.mephi.lotterydrawservice.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.dto.request.InvoiceRequestDto;
import ru.mephi.lotterydrawservice.dto.response.InvoiceResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawNotFoundException;
import ru.mephi.lotterydrawservice.exception.InvalidTicketDataException;
import ru.mephi.lotterydrawservice.exception.InvoiceNotFoundException;
import ru.mephi.lotterydrawservice.mapper.InvoiceMapper;
import ru.mephi.lotterydrawservice.model.Invoice;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.model.enums.InvoiceStatus;
import ru.mephi.lotterydrawservice.repository.InvoiceRepository;
import ru.mephi.lotterydrawservice.security.AuthUser;
import ru.mephi.lotterydrawservice.service.DrawService;
import ru.mephi.lotterydrawservice.service.InvoiceService;

import java.util.List;
import java.util.Objects;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final DrawService drawService;

    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, DrawService drawService, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.drawService = drawService;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional
    @Override
    public InvoiceResponseDto register(InvoiceRequestDto invoiceRequestDto) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = authUser.getUser();

        JSONObject ticketData;
        try {
            ticketData = new JSONObject(invoiceRequestDto.getTicketData());
        } catch (JSONException err) {
            throw new InvalidTicketDataException("Invalid ticketData format.");
        }

        if (!Objects.equals(user.getId(), ticketData.getLong("userId"))) {
            throw new SecurityException("Authenticated user does not match ticket owner.");
        }

        if (!drawService.checkDrawExistsAndActive(ticketData.getLong("drawId"))) {
            throw new DrawNotFoundException("The specified draw does not exist or is not active.");
        }

        Invoice invoice = new Invoice();
        invoice.setTicketData(invoiceRequestDto.getTicketData());
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
    public Invoice findInvoiceById(long invoiceId) throws InvoiceNotFoundException {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice with ID " + invoiceId + " not found."));
    }
}
