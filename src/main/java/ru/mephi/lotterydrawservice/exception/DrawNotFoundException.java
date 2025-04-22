package ru.mephi.lotterydrawservice.exception;

public class DrawNotFoundException extends RuntimeException {
    public DrawNotFoundException(String message) {
        super(message);
    }
}
