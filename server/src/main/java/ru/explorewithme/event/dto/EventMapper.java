package ru.explorewithme.event.dto;

import ru.explorewithme.category.repository.CategoryRepository;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.model.EventState;
import ru.explorewithme.location.repository.LocationRepository;
import ru.explorewithme.user.dto.UserMapper;
import ru.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    public static EventDto toEventDto(Event event) {
        EventDto eventDto = EventDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category((event.getCategory()).getId())
                .confirmedRequests(event.getConfirmedRequest())
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .build();

        if (event.getPublishedOn() != null) {
            eventDto.setPublishedOn(event.getPublishedOn()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return eventDto;
    }

    public static Event toEvent(Long userId,
                                EventDtoIn eventDto,
                                UserRepository userRepository,
                                LocationRepository locationRepository,
                                CategoryRepository categoryRepository) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(categoryRepository.findById(eventDto.getCategory()).get());
        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setDescription(eventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setInitiator(userRepository.findById(userId).get());
        event.setLocation(eventDto.getLocation());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.getRequestModeration());
        if (eventDto.getState() != null) {
            event.setState(EventState.valueOf(eventDto.getState()));
        } else {
            event.setState(EventState.PENDING);
        }
        event.setTitle(eventDto.getTitle());
        event.setViews(0);
        return event;
    }
}