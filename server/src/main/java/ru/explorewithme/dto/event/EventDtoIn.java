package ru.explorewithme.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.explorewithme.model.location.Location;

/**
 * Обьект События
 *
 * @see ru.explorewithme.model.event.Event
 */

@Data
@Builder
public class EventDtoIn {

    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    private String state;
}
