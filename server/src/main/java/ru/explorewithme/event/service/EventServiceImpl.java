package ru.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import ru.explorewithme.category.dto.CategoryMapper;
import ru.explorewithme.category.repository.CategoryRepository;
import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.dto.EventDtoIn;
import ru.explorewithme.event.dto.EventMapper;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.model.EventState;
import ru.explorewithme.event.repository.EventRepository;
import ru.explorewithme.location.repository.LocationRepository;
import ru.explorewithme.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventDto> getAllUsersEvents(Integer from, Integer size, Long id, HttpServletRequest request) {
        //TODO STATISTIC
        List<Event> result = eventRepository.findAllById(id, PageRequest.of(from, size));
        return result.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
    }

    @Override
    public EventDto postEvent(Long userId, EventDtoIn eventDto) {
        locationRepository.save(eventDto.getLocation());
        Event event = EventMapper.toEvent(userId, eventDto, userRepository, locationRepository, categoryRepository);
        return EventMapper.toEventDto(eventRepository.save(event));
    }

    @Override
    public List<EventDto> getEventsByRequests(List<Long> userIds, List<EventState> states,
                                              List<Long> categoryIds, LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd, Integer from, Integer size) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().withNano(0);
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().withNano(0).plusYears(10);
        }

        LocalDateTime finalRangeStart = rangeStart;
        LocalDateTime finalRangeEnd = rangeEnd;
        List<Event> events = eventRepository
                .findAll()
                .stream()
                .filter(event -> userIds.contains(event.getInitiator().getId())
                        && categoryIds.contains(event.getCategory().getId())
                        && event.getEventDate().isAfter(finalRangeStart) && event.getEventDate().isBefore(finalRangeEnd))
                .collect(Collectors.toList());
        return events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
    }

    @Override
    public EventDto updateEvent(Long eventId, EventDto eventDto) {
        Event event = eventRepository.findById(eventId).get();
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(eventDto.getCategory()).get());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        eventRepository.save(event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto setPublishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        event.setPublishedOn(LocalDateTime.now().withNano(0));
        event.setState(EventState.PUBLISHED);
        eventRepository.save(event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto setRejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        if (!event.getState().equals(EventState.PUBLISHED)) {
            event.setState(EventState.CANCELED);
            eventRepository.save(event);
        }
        return EventMapper.toEventDto(event);
    }
}
