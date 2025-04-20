package ru.mephi.lotterydrawservice.exception;

public class InvoiceAlreadyProcessedException extends RuntimeException {

    public InvoiceAlreadyProcessedException(String message) {
        super(message);
    }
}
