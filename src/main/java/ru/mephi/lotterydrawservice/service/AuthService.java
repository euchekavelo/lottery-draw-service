package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.UserShortRequestDto;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.response.TokenResponseDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;
import ru.mephi.lotterydrawservice.model.User;

public interface AuthService {

    UserResponseDto register(UserRequestDto userRequestDto);

    TokenResponseDto login(UserShortRequestDto userShortRequestDto);

    User getAuthUser();
}
