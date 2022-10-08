package ru.explorewithme.util.event;

import ru.explorewithme.repository.category.CategoryRepository;
import ru.explorewithme.model.event.Event;
import ru.explorewithme.model.event.State;
import ru.explorewithme.repository.location.LocationRepository;
import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.dto.event.EventDtoFull;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.util.user.UserMapper;
import ru.explorewithme.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventDto toEventDto(Event event) {
        EventDto eventDto = EventDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category((event.getCategory().getId()))
                .confirmedRequests(event.getConfirmedRequest())
                .createdOn(event.getCreatedOn().format(DATA_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATA_TIME_FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .build();

        if (event.getPublishedOn() != null) {
            eventDto.setPublishedOn(event.getPublishedOn().format(DATA_TIME_FORMATTER));
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
        event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), DATA_TIME_FORMATTER));
        event.setInitiator(userRepository.findById(userId).get());
        event.setLocation(eventDto.getLocation());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());
        if (eventDto.getState() != null) {
            event.setState(State.valueOf(eventDto.getState()));
        } else {
            event.setState(State.PENDING);
        }
        event.setTitle(eventDto.getTitle());
        event.setViews(0);
        return event;
    }

    public static EventDtoFull toEventDtoFull(Event event) {
        EventDtoFull eventDtoFull = EventDtoFull.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category((event.getCategory()))
                .confirmedRequests(event.getConfirmedRequest())
                .createdOn(event.getCreatedOn().format(DATA_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATA_TIME_FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

        if (event.getPublishedOn() != null) {
            eventDtoFull.setPublishedOn(event.getPublishedOn().format(DATA_TIME_FORMATTER));
        }
        return eventDtoFull;
    }
}
