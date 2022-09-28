package ru.explorewithme.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmptyListException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
