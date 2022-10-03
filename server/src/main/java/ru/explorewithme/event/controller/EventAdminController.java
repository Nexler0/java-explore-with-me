package ru.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.model.EventState;
import ru.explorewithme.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    List<EventDto> getEventsAdmin(@RequestParam(name = "users", required = false) List<Long> userIds,
                                  @RequestParam(required = false) List<EventState> states,
                                  @RequestParam(required = false, name = "categories") List<Long> categoryIds,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          LocalDateTime rangeEnd,
                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.getEventsByRequests(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    EventDto updateEvent(@PathVariable Long eventId, @RequestBody EventDto eventDto) {
        return eventService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    EventDto publishEvent(@PathVariable Long eventId){
        return eventService.setPublishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    EventDto rejectEvent(@PathVariable Long eventId){
        return eventService.setRejectEvent(eventId);
    }

}
