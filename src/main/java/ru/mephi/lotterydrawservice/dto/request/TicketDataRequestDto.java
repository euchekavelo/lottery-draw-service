package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

@Data
public class TicketDataRequestDto {

    private long userId;
    private long drawId;
    private String numbers;
}
