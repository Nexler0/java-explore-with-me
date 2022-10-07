package ru.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.user.dto.dto.EventDto;
import ru.explorewithme.user.dto.dto.EventDtoFull;
import ru.explorewithme.user.dto.dto.EventDtoIn;
import ru.explorewithme.event.service.EventService;
import ru.explorewithme.request.dto.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventDtoFull> getAllUsersEvents(@RequestParam(required = false) String text,
                                                @RequestParam(required = false) List<Long> categories,
                                                @RequestParam(required = false) Boolean paid,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeStart,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeEnd,
                                                @RequestParam(required = false, defaultValue = "false")
                                                        Boolean onlyAvailable,
                                                @RequestParam(name = "sort", required = false) String sort,
                                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @RequestParam(required = false, defaultValue = "10") Integer size,
                                                HttpServletRequest request) {
        return eventService.getAllUsersEventsByParameter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping("/events/{eventId}")
    public EventDtoFull getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventDtoFull> getAllUsersEvents(@RequestParam(name = "from", defaultValue = "0", required = false)
                                                        Integer from,
                                                @RequestParam(name = "size", defaultValue = "10", required = false)
                                                        Integer size,
                                                @PathVariable Long userId,
                                                HttpServletRequest request) {
        return eventService.getAllUsersEvents(from, size, userId, request);
    }

    @PostMapping("/users/{userId}/events")
    public EventDtoFull postEventByUser(@RequestBody EventDtoIn eventDtoIn,
                                        @PathVariable Long userId) {
        return eventService.postEventByUser(userId, eventDtoIn);
    }

    @PatchMapping("/users/{userId}/events")
    public EventDto updateEventByUser(@RequestBody EventDtoIn eventDtoIn,
                                      @PathVariable Long userId) {
        return eventService.updateEventByUser(userId, eventDtoIn);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventDtoFull getUserEvent(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        return eventService.getUserEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventDto cancelUserEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        return eventService.cancelUserEvent(userId, eventId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserEventRequests(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        return eventService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmUserRequest(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @PathVariable Long reqId) {
        return eventService.confirmUserRequest(userId, eventId, reqId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectUserRequest(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @PathVariable Long reqId) {
        return eventService.rejectUserRequest(userId, eventId, reqId);
    }
}
