package ru.mephi.lotterydrawservice.dto;

import lombok.Data;

@Data
public class TicketDataDto {

    private long userId;
    private long drawId;
    private String numbers;
}
