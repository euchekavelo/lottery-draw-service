package ru.mephi.lotterydrawservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mephi.lotterydrawservice.dto.response.ResponseDto;
import ru.mephi.lotterydrawservice.exception.*;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({DrawResultNotFoundException.class, TicketNotFoundException.class, UserNotFoundException.class,
            DrawNotFoundException.class, InvoiceNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ResponseDto> handleExceptionForNotFoundHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getResponseDto(ex.getMessage()));
    }

    @ExceptionHandler({RegistrationException.class, InvalidPaymentDataException.class, InvalidTicketDataException.class,
            InvoiceAlreadyProcessedException.class, IllegalArgumentException.class, UnsupportedOperationException.class,
            SecurityException.class})
    public ResponseEntity<ResponseDto> handleExceptionForBadRequestHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getResponseDto(ex.getMessage()));
    }

    private ResponseDto getResponseDto(String message) {
        return ResponseDto.builder()
                .message(message)
                .build();
    }
}
