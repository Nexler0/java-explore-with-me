package ru.explorewithme.exception;

import lombok.AllArgsConstructor;

/**
 * Кастомное исключение
 *
 * @see ErrorHandler
 */

@AllArgsConstructor
public class EmptyListException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
