package ru.mephi.lotterydrawservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mephi.lotterydrawservice.dto.response.ErrorResponseDto;
import ru.mephi.lotterydrawservice.exception.*;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({DrawResultNotFoundException.class, TicketNotFoundException.class, UserNotFoundException.class,
            DrawNotFoundException.class, InvoiceNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponseDto> handleExceptionForNotFoundHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({RegistrationException.class, InvalidPaymentDataException.class, InvalidTicketDataException.class,
            InvoiceAlreadyProcessedException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleExceptionForBadRequestHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(ex.getMessage()));
    }

    private ErrorResponseDto getErrorResponse(String message) {
        return ErrorResponseDto.builder()
                .message(message)
                .build();
    }
}
