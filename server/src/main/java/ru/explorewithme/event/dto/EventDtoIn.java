package ru.explorewithme.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.explorewithme.location.model.Location;

@Data
@Builder
public class EventDtoIn {

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    private String state;
}
