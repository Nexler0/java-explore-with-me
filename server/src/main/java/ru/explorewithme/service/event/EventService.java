package ru.explorewithme.service.event;

import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.dto.event.EventDtoFull;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.model.event.State;
import ru.explorewithme.dto.request.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventDtoFull> getAllUsersEventsByParameter(String text,
                                                    List<Long> categories,
                                                    Boolean paid,
                                                    LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd,
                                                    Boolean onlyAvailable,
                                                    String sort,
                                                    Integer from,
                                                    Integer size,
                                                    HttpServletRequest request);

    List<EventDtoFull> getAllUsersEvents(Integer from, Integer size, Long userId, HttpServletRequest request);

    EventDtoFull postEventByUser(Long userId, EventDtoIn eventDto);

    List<EventDto> getEventsByRequests(List<Long> userIds, List<State> states,
                                       List<Long> categoryIds, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto updateEvent(Long eventId, EventDto eventDto);

    EventDto setPublishEvent(Long eventId);

    EventDto setRejectEvent(Long eventId);

    EventDto updateEventByUser(Long userId, EventDtoIn eventDtoIn);

    EventDtoFull getUserEvent(Long userId, Long eventId);

    EventDto cancelUserEvent(Long userId, Long eventId);

    EventDtoFull getEvent(Long eventId, HttpServletRequest request);

    List<RequestDto> getUserEventRequests(Long userId, Long eventId);

    RequestDto confirmUserRequest(Long userId, Long eventId, Long reqId);

    RequestDto rejectUserRequest(Long userId, Long eventId, Long reqId);


}
