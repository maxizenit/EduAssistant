package ru.itmo.eduassistant.commons.exception;

public class DialogNotFoundException extends RuntimeException {
    public DialogNotFoundException(String message) {
        super(message);
    }
}
