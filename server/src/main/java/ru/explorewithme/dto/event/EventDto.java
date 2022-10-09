package ru.explorewithme.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.explorewithme.model.location.Location;
import ru.explorewithme.dto.user.UserShortDto;

/**
 * Обьект События
 *
 * @see ru.explorewithme.model.event.Event
 */

@Data
@Builder
public class EventDto {

    private Long id;
    private String annotation;
    private Long category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private String publishedOn;
    private String state;
    private Boolean requestModeration;
    private String title;
}
