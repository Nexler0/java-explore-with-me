package ru.explorewithme.event.dto;

import lombok.Builder;
import lombok.Data;
import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.user.dto.UserShortDto;

@Data
@Builder
public class EventShortDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
