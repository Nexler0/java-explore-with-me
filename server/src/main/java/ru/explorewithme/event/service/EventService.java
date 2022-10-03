package ru.explorewithme.event.service;

import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.dto.EventDtoIn;
import ru.explorewithme.event.model.EventState;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventDto> getAllUsersEvents(Integer from, Integer size, Long id, HttpServletRequest request);

    EventDto postEvent(Long userId, EventDtoIn eventDto);

    List<EventDto> getEventsByRequests(List<Long> userIds, List<EventState> states,
                                       List<Long> categoryIds, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto updateEvent(Long eventId, EventDto eventDto);

    EventDto setPublishEvent(Long eventId);

    EventDto setRejectEvent(Long eventId);
}
