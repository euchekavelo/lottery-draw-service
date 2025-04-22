package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.InvoiceStatus;

import java.time.LocalDateTime;

@Data
public class InvoiceResponseDto {
    private long id;
    private String ticketData;
    private LocalDateTime registerTime;
    private InvoiceStatus status;
}
