package ru.mephi.lotterydrawservice.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {

    private long id;
    private String login;
    private String fullName;
    private String role;
}
