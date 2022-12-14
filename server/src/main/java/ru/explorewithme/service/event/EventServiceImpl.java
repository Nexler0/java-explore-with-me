package ru.explorewithme.service.event;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.explorewithme.repository.category.CategoryRepository;
import ru.explorewithme.dto.statisticDto.StatisticDto;
import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.dto.event.EventDtoFull;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.util.event.EventMapper;
import ru.explorewithme.model.event.Event;
import ru.explorewithme.model.event.State;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.exception.ForbiddenException;
import ru.explorewithme.exception.ValidationException;
import ru.explorewithme.repository.location.LocationRepository;
import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.util.request.RequestMapper;
import ru.explorewithme.model.request.Request;
import ru.explorewithme.repository.request.RequestRepository;
import ru.explorewithme.repository.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.explorewithme.client.httpclient.HttpClient.getViews;
import static ru.explorewithme.client.httpclient.HttpClient.sendStatistic;

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

    @Value("${ewm_statistic_url}")
    private String uriServer;

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
        StatisticDto statisticDto = new StatisticDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().withNano(0).format(DATA_TIME_FORMATTER));
        Gson gson = new Gson();
        sendStatistic(uriServer, gson.toJson(statisticDto));
        result.forEach(event -> event.setViews(getViews(uriServer, List.of("/events"), false)));
        log.info("Events getAllUsersEventsByParameter: {}", result);
        return result.stream().map(EventMapper::toEventDtoFull).collect(Collectors.toList());
    }

    @Override
    public EventDtoFull getEvent(Long eventId, HttpServletRequest request) {
        Event result = eventRepository.findById(eventId).get();
        StatisticDto statisticDto = new StatisticDto(null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().withNano(0).format(DATA_TIME_FORMATTER));
        Gson gson = new Gson();
        sendStatistic(uriServer, gson.toJson(statisticDto));
        result.setViews(getViews(uriServer, List.of("/events/" + result.getId()), false));
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
            throw new ValidationException("???????????? ??????????????, ???????????????? ??????????");
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
            throw new ValidationException("???????????????? ?????????? ?????? ???????????? ??????????????");
        }
    }

    @Override
    public EventDtoFull getUserEvent(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        if (event.getInitiator().getId().equals(userId)) {
            log.info("Events getUserEvent: {}", event);
            return EventMapper.toEventDtoFull(event);
        } else {
            throw new ValidationException("???????????????????????? ???? ???????????????? ???????????????????? ??????????????");
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
            throw new ValidationException("???????????????????????? ???? ???????????????? ???????????????????? ??????????????");
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
            throw new ForbiddenException("?????????????????? ?????????? ????????????");
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
            throw new ValidationException("???????????????????????? ???? ?????????? ?????????????????????? ?????????????? ???????????? ????????");
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
            throw new ValidationException("???????????????????????? ???? ?????????? ?????????????????????? ?????????????? ???????????? ????????");
        }
    }
}
