package ru.mephi.lotterydrawservice.exception;

public class InvalidTicketDataException extends RuntimeException {

    public InvalidTicketDataException(String message) {
        super(message);
    }
}
