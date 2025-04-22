package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.UserShortRequestDto;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.response.TokenResponseDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRequestDto userRequestDto);

    TokenResponseDto login(UserShortRequestDto userShortRequestDto);
}
