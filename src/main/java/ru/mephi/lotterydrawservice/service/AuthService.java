package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.UserShortRequestDto;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.response.TokenResponseDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;
import ru.mephi.lotterydrawservice.exception.RegistrationException;
import ru.mephi.lotterydrawservice.exception.UserNotFoundException;

public interface AuthService {

    UserResponseDto register(UserRequestDto userRequestDto) throws RegistrationException;

    TokenResponseDto login(UserShortRequestDto userShortRequestDto) throws UserNotFoundException;
}
