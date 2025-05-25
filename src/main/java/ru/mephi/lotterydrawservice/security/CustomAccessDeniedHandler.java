package ru.mephi.lotterydrawservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.mephi.lotterydrawservice.dto.response.ResponseDto;
import ru.mephi.lotterydrawservice.service.AuthService;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final AuthService authService;

    @Autowired
    public CustomAccessDeniedHandler(ObjectMapper objectMapper, AuthService authService) {
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        long currentUserId = authService.getAuthUser().getId();
        String requestedUrl = request.getRequestURI();

        try {
            MDC.put("user_id", Long.toString(currentUserId));
            MDC.put("requested_url", requestedUrl);
            log.warn("Access denied");
        } finally {
            MDC.clear();
        }

        ResponseDto responseDto = ResponseDto.builder()
                .message(accessDeniedException.getMessage())
                .result(false)
                .build();

        objectMapper.writeValue(response.getOutputStream(), responseDto);
    }
}
