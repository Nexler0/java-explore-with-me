package ru.explorewithme.service.event;

import ru.explorewithme.dto.event.EventDto;
import ru.explorewithme.dto.event.EventDtoFull;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.model.event.State;
import ru.explorewithme.dto.request.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы сСобитиями
 *
 * @see EventService
 */

public interface EventService {
    /**
     * Получение списка событий по заданным параметрам
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

    /**
     * Private API получение списка событий пользователя
     *
     * @param from    номер страницы для пагинации
     * @param size    количество записей на странице
     * @param userId  идентификтора пользователя
     * @param request для получение информации о пользователе (Ip, Uri)
     */
    List<EventDtoFull> getAllUsersEvents(Integer from, Integer size, Long userId, HttpServletRequest request);

    /**
     * Private API Публикация События пользователем
     *
     * @param eventDtoIn Обьект события
     * @param userId     идентификатор пользователя
     */
    EventDtoFull postEventByUser(Long userId, EventDtoIn eventDtoIn);

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
    List<EventDto> getEventsByRequests(List<Long> userIds, List<State> states,
                                       List<Long> categoryIds, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Обновление события
     *
     * @param eventId  идентификатор события
     * @param eventDto Обьект события
     */
    EventDto updateEvent(Long eventId, EventDto eventDto);

    /**
     * Изменение статуса события на опубликовано
     *
     * @param eventId идентификатор события
     */
    EventDto setPublishEvent(Long eventId);

    /**
     * Изменение статуса события на отклонено
     *
     * @param eventId идентификатор события
     */
    EventDto setRejectEvent(Long eventId);

    /**
     * Private API Обновление события пользователем
     *
     * @param eventDtoIn Обьект события
     * @param userId     идентификатор пользователя
     */
    EventDto updateEventByUser(Long userId, EventDtoIn eventDtoIn);

    /**
     * Private API Получение собтия пользователем по идентификатору события
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    EventDtoFull getUserEvent(Long userId, Long eventId);

    /**
     * Private API Отмена события пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    EventDto cancelUserEvent(Long userId, Long eventId);

    /**
     * Public Api Получение события по идентификтору
     *
     * @param eventId идентификатор
     * @param request для получение информации о пользователе (Ip, Uri)
     */
    EventDtoFull getEvent(Long eventId, HttpServletRequest request);

    /**
     * Private API Получение спика запросов на участие пользователем для конкретного события
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    List<RequestDto> getUserEventRequests(Long userId, Long eventId);

    /**
     * Private API подтверждение заявки на участие пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @param reqId   идентификатор запроса
     */
    RequestDto confirmUserRequest(Long userId, Long eventId, Long reqId);

    /**
     * Private API Отклонение заявки на участие пользователем
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @param reqId   идентификатор запроса
     */
    RequestDto rejectUserRequest(Long userId, Long eventId, Long reqId);
}
