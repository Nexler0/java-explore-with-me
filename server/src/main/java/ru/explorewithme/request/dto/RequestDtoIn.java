package ru.explorewithme.request.dto;

import lombok.Getter;
import lombok.Setter;

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
