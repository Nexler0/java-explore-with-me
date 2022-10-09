package ru.explorewithme.controller.client.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.service.user.UserService;

import java.util.List;

/**
 * Private API для работы с запросами пользоватлей
 *
 * @see UserController
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Полученеи всех запросов пользователя
     *
     * @param userId идентификатор пользователя
     */
    @GetMapping("/{userId}/requests")
    public List<RequestDto> getAllUserRequests(@PathVariable Long userId) {
        return userService.getAllUserRequests(userId);
    }

    /**
     * Добавление запроса пользователя на участие в событии
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    @PostMapping("/{userId}/requests")
    public RequestDto postUserRequest(@PathVariable Long userId,
                                      @RequestParam(name = "eventId") Long eventId) {
        return userService.postUserRequest(userId, eventId);
    }

    /**
     * Отмена запроса на участие
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequestByUser(@PathVariable Long userId, @PathVariable Long requestId) {
        return userService.cancelRequestByUser(userId, requestId);
    }
}
