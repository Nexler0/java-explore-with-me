package ru.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.explorewithme.category.repository.CategoryRepository;
import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.dto.EventDtoFull;
import ru.explorewithme.event.dto.EventDtoIn;
import ru.explorewithme.event.dto.EventMapper;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.model.State;
import ru.explorewithme.event.repository.EventRepository;
import ru.explorewithme.exception.ForbiddenException;
import ru.explorewithme.exception.ValidationException;
import ru.explorewithme.location.repository.LocationRepository;
import ru.explorewithme.request.dto.RequestDto;
import ru.explorewithme.request.dto.RequestMapper;
import ru.explorewithme.request.model.Request;
import ru.explorewithme.request.repository.RequestRepository;
import ru.explorewithme.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<EventDtoFull> getAllUsersEventsByParameter(String text,
                                                           List<Long> categories,
                                                           Boolean paid,
                                                           LocalDateTime rangeStart,
                                                           LocalDateTime rangeEnd,
                                                           Boolean onlyAvailable,
                                                           String sort,
                                                           Integer from,
                                                           Integer size,
                                                           HttpServletRequest request) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().withNano(0);
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().withNano(0).plusYears(100);
        }
        List<Event> result = eventRepository.findAllByStateAndCreatedOnIsBetween(
                State.PUBLISHED,
                rangeStart,
                rangeEnd,
                PageRequest.of(from, size)).toList();

        result = result.stream().filter(event ->
                        event.getAnnotation().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
                                && categories.contains(event.getCategory().getId())
                                && event.getPaid().equals(paid))
                .collect(Collectors.toList());

        if (onlyAvailable) {
            result = result.stream().filter(event -> event.getParticipantLimit() < event.getConfirmedRequest())
                    .collect(Collectors.toList());
        }
        log.info("Events getAllUsersEventsByParameter: {}", result);
        return result.stream().map(EventMapper::toEventDtoFull).collect(Collectors.toList());
    }

    @Override
    public EventDtoFull getEvent(Long eventId) {
        Event result = eventRepository.findById(eventId).get();
        log.info("Events getEvent: {}", result);
        return EventMapper.toEventDtoFull(result);
    }

    @Override
    public List<EventDtoFull> getAllUsersEvents(Integer from, Integer size, Long userId, HttpServletRequest request) {
        List<Event> result = eventRepository.findAllByInitiatorId(userId, PageRequest.of(from, size));
        log.info("Events getAllUsersEvents: {}", result);
        return result.stream().map(EventMapper::toEventDtoFull).collect(Collectors.toList());
    }

    @Override
    public EventDtoFull postEventByUser(Long userId, EventDtoIn eventDto) {
        if (LocalDateTime.parse(eventDto.getEventDate(), DATA_TIME_FORMATTER)
                .isAfter(LocalDateTime.now().withNano(0).plusHours(2))) {
            locationRepository.save(eventDto.getLocation());
            Event event = EventMapper.toEvent(userId, eventDto, userRepository, locationRepository, categoryRepository);
            log.info("Events postEventByUser: {}", event);
            return EventMapper.toEventDtoFull(eventRepository.save(event));
        } else {
            throw new ValidationException("Ошибка события, неверное время");
        }
    }

    @Override
    public List<EventDto> getEventsByRequests(List<Long> userIds, List<State> states,
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
                        && event.getEventDate().isAfter(finalRangeStart)
                        && event.getEventDate().isBefore(finalRangeEnd))
                .collect(Collectors.toList());
        log.info("Events getEventsByRequests: {}", events);
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
            event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), DATA_TIME_FORMATTER));
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
        log.info("Events updateEvent: {}", event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto setPublishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        event.setPublishedOn(LocalDateTime.now().withNano(0));
        event.setState(State.PUBLISHED);
        eventRepository.save(event);
        log.info("Events setPublishEvent: {}", event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto setRejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        if (!event.getState().equals(State.PUBLISHED)) {
            event.setState(State.CANCELED);
            eventRepository.save(event);
        }
        log.info("Events setRejectEvent: {}", event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto updateEventByUser(Long userId, EventDtoIn eventDto) {
        Event event = eventRepository.findByInitiatorId(userId);
        if (event.getState().equals(State.CANCELED) || event.getRequestModeration()
                || event.getEventDate().isAfter(LocalDateTime.now().withNano(0).plusHours(2))) {
            if (event.getState().equals(State.CANCELED)) {
                event.setRequestModeration(true);
            }
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
                event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), DATA_TIME_FORMATTER));
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
            log.info("Events updateEventByUser: {}", event);
            return EventMapper.toEventDto(event);
        } else {
            throw new ValidationException("Неверное время или статус события");
        }
    }

    @Override
    public EventDtoFull getUserEvent(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        if (event.getInitiator().getId().equals(userId)) {
            log.info("Events getUserEvent: {}", event);
            return EventMapper.toEventDtoFull(event);
        } else {
            throw new ValidationException("Пользователь не является создателем события");
        }
    }

    @Override
    public EventDto cancelUserEvent(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        if (event.getInitiator().getId().equals(userId) && event.getRequestModeration()) {
            event.setState(State.CANCELED);
            eventRepository.save(event);
            log.info("Events cancelUserEvent: {}", event);
            return EventMapper.toEventDto(event);
        } else {
            throw new ValidationException("Пользователь не является создателем события");
        }
    }

    @Override
    public List<RequestDto> getUserEventRequests(Long userId, Long eventId) {
        List<Request> requests = requestRepository.findByEventId(eventId);
        log.info("Events getUserEventRequests: {}", requests);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    public RequestDto confirmUserRequest(Long userId, Long eventId, Long reqId) {
        Event event = eventRepository.findById(eventId).get();
        Request request = requestRepository.findById(reqId).get();
        if (event.getConfirmedRequest() >= requestRepository.countByEventId(eventId)) {
            throw new ForbiddenException("Достигнут лимит заявок");
        }
        if (event.getParticipantLimit() == 0 || event.getRequestModeration().equals(false)) {
            request.setStatus(State.CONFIRMED);
        }
        if (userId.equals(event.getInitiator().getId()) && !userId.equals(request.getRequester().getId())) {
            request.setStatus(State.CONFIRMED);
            if (Objects.equals(event.getConfirmedRequest(), requestRepository.countByEventId(eventId))) {
                for (Request r : requestRepository.findAllByCreatedAfter(request.getCreated())) {
                    r.setStatus(State.REJECTED);
                    requestRepository.save(r);
                }
            }
            event.setConfirmedRequest(event.getConfirmedRequest() + 1);
            eventRepository.save(event);
            return RequestMapper.toRequestDto(request);
        } else {
            throw new ValidationException("Пользователь не может подтвердить участие самого себя");
        }

    }

    @Override
    public RequestDto rejectUserRequest(Long userId, Long eventId, Long reqId) {
        Event event = eventRepository.findById(eventId).get();
        Request request = requestRepository.findById(reqId).get();
        if (userId.equals(event.getInitiator().getId()) && !userId.equals(request.getRequester().getId())) {
            request.setStatus(State.REJECTED);
            return RequestMapper.toRequestDto(request);
        } else {
            throw new ValidationException("Пользователь не может подтвердить участие самого себя");
        }
    }
}
