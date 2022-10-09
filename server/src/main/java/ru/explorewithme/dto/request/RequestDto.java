package ru.explorewithme.dto.request;

import lombok.*;

/**
 * Обьект Запроса
 *
 * @see ru.explorewithme.model.request.Request
 */

@Getter
@Setter
public class RequestDto {

    private Long id;
    private String created;
    private Long event;
    private Long requester;
    private String status;

    public RequestDto(Long id, String created, Long event, Long requester, String status) {
        this.id = id;
        this.created = created;
        this.event = event;
        this.requester = requester;
        this.status = status;
    }
}
