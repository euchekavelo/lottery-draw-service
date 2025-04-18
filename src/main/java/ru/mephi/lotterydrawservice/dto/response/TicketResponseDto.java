package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;

@Data
public class TicketResponseDto {

    private long id;
    private long userId;
    private long drawId;
    private String data;
    private TicketStatus status;
}