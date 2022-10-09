package ru.explorewithme.service.user;

import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.dto.user.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Получение спика всех пользователей по параметрам
     *
     * @param userIds список идентификаторов пользователей
     * @param size    количество записе на странице
     * @param from    номер страницы для пагинации
     */
    List<UserDto> getAllUsers(List<Long> userIds, Integer from, Integer size);

    /**
     * Добавление пользователя
     *
     * @param userDto Обьект пользователь
     */
    UserDto addUser(UserDto userDto);

    /**
     * Удаление пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     */
    void deleteUser(Long id);

    /**
     * Полученеи всех запросов пользователя
     *
     * @param userId идентификатор пользователя
     */
    List<RequestDto> getAllUserRequests(Long userId);

    /**
     * Добавление запроса пользователя на участие в событии
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    RequestDto postUserRequest(Long userId, Long eventId);

    /**
     * Отмена запроса на участие
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     */
    RequestDto cancelRequestByUser(Long userId, Long requestId);
}
