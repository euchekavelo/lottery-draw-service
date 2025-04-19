package ru.mephi.lotterydrawservice.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {

    private String fullName;
    private String login;
    private String password;
    private String role;
}
