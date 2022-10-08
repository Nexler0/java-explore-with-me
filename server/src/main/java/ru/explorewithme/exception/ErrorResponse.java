package ru.explorewithme.exception;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Обьект формирования отчета об ошибке
 *
 * @see ErrorHandler
 */

@Data
public class ErrorResponse {
    private final String error;
    private final String reason;
    private final String status;
    private final LocalDateTime timestamp = LocalDateTime.now().withNano(0);
}
