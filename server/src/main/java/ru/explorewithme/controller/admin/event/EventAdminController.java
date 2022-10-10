package ru.explorewithme.controller.admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.model.event.State;
import ru.explorewithme.service.event.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API для работы с событиями для администратора
 *
 * @see EventAdminController
 */

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    /**
     * Получение списка событий по заданным параметрам
     *
     * @param userIds     идентификатор пользователя
     * @param states      статус события
     * @param categoryIds котегории события
     * @param rangeStart  период начала поиска
     * @param rangeEnd    период окончани поиска
     * @param from        номер страницы для пагинации
     * @param size        количество записей на странице
     */
    @GetMapping
    List<EventDto> getEventsAdmin(@RequestParam(name = "users", required = false) List<Long> userIds,
                                  @RequestParam(required = false) List<State> states,
                                  @RequestParam(required = false, name = "categories") List<Long> categoryIds,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          LocalDateTime rangeEnd,
                                  @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsByRequests(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }

    /**
     * Обновление события
     *
     * @param eventId  идентификатор события
     * @param eventDto Обьект события
     */
    @PutMapping("/{eventId}")
    EventDto updateEvent(@PathVariable Long eventId, @RequestBody EventDto eventDto) {
        return eventService.updateEvent(eventId, eventDto);
    }

    /**
     * Изменение статуса события на опубликовано
     *
     * @param eventId идентификатор события
     */
    @PatchMapping("/{eventId}/publish")
    EventDto publishEvent(@PathVariable Long eventId) {
        return eventService.setPublishEvent(eventId);
    }

    /**
     * Изменение статуса события на отклонено
     *
     * @param eventId идентификатор события
     */
    @PatchMapping("/{eventId}/reject")
    EventDto rejectEvent(@PathVariable Long eventId) {
        return eventService.setRejectEvent(eventId);
    }

}
