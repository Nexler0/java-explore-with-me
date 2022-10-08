package ru.explorewithme.controller.client.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.dto.event.EventDtoFull;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.service.event.EventService;
import ru.explorewithme.dto.request.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API для пользователей, работа с событиями
 *
 * @see EventController
 */

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * Public Api Получение списка событий по заданным параметрам
     *
     * @param text          текст запроса
     * @param categories    список категорий
     * @param paid          статус для платы за событие
     * @param rangeStart    период начала поиска
     * @param rangeEnd      период окончани поиска
     * @param onlyAvailable статус возможности участвовать
     * @param sort          сортировка по дате или просмотрам
     * @param from          номер страницы для пагинации
     * @param size          количество записей на странице
     * @param request       для получение информации о пользователе (Ip, Uri)
     */
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

    /**
     * Public Api Получение события по идентификтору
     *
     * @param eventId идентификатор
     * @param request для получение информации о пользователе (Ip, Uri)
     */
    @GetMapping("/events/{eventId}")
    public EventDtoFull getEvent(@PathVariable Long eventId, HttpServletRequest request) {
        return eventService.getEvent(eventId, request);
    }

    /**
     * Private API получение списка событий пользователя
     *
     * @param from    номер страницы для пагинации
     * @param size    количество записей на странице
     * @param userId  идентификтора пользователя
     * @param request для получение информации о пользователе (Ip, Uri)
     */
    @GetMapping("/users/{userId}/events")
    public List<EventDtoFull> getAllUsersEvents(@RequestParam(name = "from", defaultValue = "0", required = false)
                                                        Integer from,
                                                @RequestParam(name = "size", defaultValue = "10", required = false)
                                                        Integer size,
                                                @PathVariable Long userId,
                                                HttpServletRequest request) {
        return eventService.getAllUsersEvents(from, size, userId, request);
    }

    /**
     * Private API Публикация События пользователем
     *
     * @param eventDtoIn Обьект события
     * @param userId     идентификатор пользователя
     */
    @PostMapping("/users/{userId}/events")
    public EventDtoFull postEventByUser(@RequestBody EventDtoIn eventDtoIn,
                                        @PathVariable Long userId) {
        return eventService.postEventByUser(userId, eventDtoIn);
    }

    /**
     * Private API Обновление события пользователем
     *
     * @param eventDtoIn Обьект события
     * @param userId     идентификатор пользователя
     */
    @PatchMapping("/users/{userId}/events")
    public EventDto updateEventByUser(@RequestBody EventDtoIn eventDtoIn,
                                      @PathVariable Long userId) {
        return eventService.updateEventByUser(userId, eventDtoIn);
    }

    /**
     * Private API Получение собтия пользователем по идентификатору события
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventDtoFull getUserEvent(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        return eventService.getUserEvent(userId, eventId);
    }

    /**
     * Private API Отмена события пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventDto cancelUserEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        return eventService.cancelUserEvent(userId, eventId);
    }

    /**
     * Private API Получение спика запросов на участие пользователем для конкретного события
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserEventRequests(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        return eventService.getUserEventRequests(userId, eventId);
    }

    /**
     * Private API подтверждение заявки на участие пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @param reqId   идентификатор запроса
     */
    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmUserRequest(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @PathVariable Long reqId) {
        return eventService.confirmUserRequest(userId, eventId, reqId);
    }

    /**
     * Private API Отклонение заявки на участие пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @param reqId   идентификатор запроса
     */
    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectUserRequest(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @PathVariable Long reqId) {
        return eventService.rejectUserRequest(userId, eventId, reqId);
    }
}
