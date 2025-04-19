package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

@Data
public class UserShortRequestDto {

    private String login;
    private String password;
}
