package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.lotterydrawservice.dto.request.InvoiceRequestDto;
import ru.mephi.lotterydrawservice.dto.response.InvoiceResponseDto;
import ru.mephi.lotterydrawservice.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<InvoiceResponseDto> register(@RequestBody InvoiceRequestDto invoiceRequestDto) {
        return ResponseEntity.ok(invoiceService.register(invoiceRequestDto));
    }
}
