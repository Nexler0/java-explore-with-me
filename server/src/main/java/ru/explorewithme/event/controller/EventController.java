package ru.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.dto.EventDtoIn;
import ru.explorewithme.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAllUsersEvents(@RequestParam(name = "from", defaultValue = "0",
            required = false) Integer from,
                                            @RequestParam(name = "size", defaultValue = "10"
                                                    , required = false) Integer size,
                                            @PathVariable Long userId,
                                            HttpServletRequest request) {
        return eventService.getAllUsersEvents(from, size, userId, request);
    }

    @PostMapping
    public EventDto postEvent(@RequestBody EventDtoIn eventDtoIn,
                              @PathVariable Long userId) {
        return eventService.postEvent(userId, eventDtoIn);
    }
}
