package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.request.UserShortRequestDto;
import ru.mephi.lotterydrawservice.dto.response.TokenResponseDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;
import ru.mephi.lotterydrawservice.exception.RegistrationException;
import ru.mephi.lotterydrawservice.exception.UserNotFoundException;
import ru.mephi.lotterydrawservice.mapper.UserMapper;
import ru.mephi.lotterydrawservice.model.Balance;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.model.enums.Role;
import ru.mephi.lotterydrawservice.repository.UserRepository;
import ru.mephi.lotterydrawservice.security.JwtUtil;
import ru.mephi.lotterydrawservice.service.AuthService;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        String role = userRequestDto.getRole();

        if (!(role.equals(Role.ADMIN.toString()) || role.equals(Role.USER.toString()))) {
            throw new RegistrationException("Only one of two roles is available for installation: "
                    + Role.ADMIN + " or " + Role.USER + ".");
        }

        User newUser = userMapper.userRequestDtoToUser(userRequestDto);
        Balance balance = new Balance();
        balance.setAmount(new BigDecimal("0"));
        newUser.setBalance(balance);
        balance.setUser(newUser);

        return userMapper.userToUserResponseDto(userRepository.save(newUser));
    }

    @Override
    public TokenResponseDto login(UserShortRequestDto userShortRequestDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userShortRequestDto.getLogin());

        if (optionalUser.isEmpty() || !passwordEncoder.matches(userShortRequestDto.getPassword(),
                optionalUser.get().getPassword())) {

            throw new UserNotFoundException("The user with the specified data was not found.");
        }

        return TokenResponseDto.builder()
                .token(jwtUtil.generateTokenForUser(userShortRequestDto.getLogin()))
                .build();
    }
}
