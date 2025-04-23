package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;
import ru.mephi.lotterydrawservice.dto.TicketDataDto;

@Data
public class InvoiceRequestDto {

    private TicketDataDto ticketData;
}
