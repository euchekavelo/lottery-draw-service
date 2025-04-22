package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.request.UserShortRequestDto;
import ru.mephi.lotterydrawservice.dto.response.TokenResponseDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;
import ru.mephi.lotterydrawservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserShortRequestDto userShortRequestDto) {
        return ResponseEntity.ok(authService.login(userShortRequestDto));
    }
}
