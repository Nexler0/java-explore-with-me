package ru.explorewithme.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.explorewithme.location.model.Location;
import ru.explorewithme.user.dto.UserShortDto;

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
    private Integer participantLimit;
    private String publishedOn;
    private String state;
    private Boolean requestModeration;
    private String title;
}
