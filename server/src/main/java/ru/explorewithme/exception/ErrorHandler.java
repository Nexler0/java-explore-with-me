package ru.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * Класс для отлавливания ошибок во время работы сервиса
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    /**
     * BAD_REQUEST Exception
     *
     * @param e отлавливаемая ошибка
     */
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ErrorResponse incorrectParameter(final ValidationException e) {
        return new ErrorResponse(Arrays.toString(e.getStackTrace()), e.getMessage(), "400");
    }

    /**
     * NOT_FOUND Exception
     *
     * @param e отлавливаемая ошибка
     */
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorResponse incorrectParameter(final NotFoundException e) {
        return new ErrorResponse(Arrays.toString(e.getStackTrace()), e.getMessage(), "404");
    }

    /**
     * FORBIDDEN Exception
     *
     * @param e отлавливаемая ошибка
     */
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ErrorResponse incorrectParameter(final ForbiddenException e) {
        return new ErrorResponse(Arrays.toString(e.getStackTrace()), e.getMessage(), "404");
    }

    /**
     * CONFLICT Exception
     *
     * @param e отлавливаемая ошибка
     */
    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT) //409
    public ErrorResponse incorrectParameter(final ConflictException e) {
        return new ErrorResponse(Arrays.toString(e.getStackTrace()), e.getMessage(), "409");
    }

    /**
     * INTERNAL_SERVER_ERROR Exception
     *
     * @param e отлавливаемая ошибка
     */
    @ExceptionHandler({EmptyListException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ErrorResponse incorrectParameter(final RuntimeException e) {
        return new ErrorResponse(Arrays.toString(e.getStackTrace()), e.getMessage(), "500");
    }

}
