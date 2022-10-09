package ru.explorewithme.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Обьект Запроса
 *
 * @see ru.explorewithme.model.request.Request
 */

@Getter
@Setter
public class RequestDtoIn {
    private Long userId;
    private Long eventId;

    public RequestDtoIn(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
}
