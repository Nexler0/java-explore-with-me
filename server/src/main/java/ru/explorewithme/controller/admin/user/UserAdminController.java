package ru.explorewithme.controller.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.service.user.UserService;
import ru.explorewithme.dto.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * API для работы с пользователями для администратора
 *
 * @see UserAdminController
 */

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    /**
     * Получение спика всех пользователей по параметрам
     *
     * @param userIds список идентификаторов пользователей
     * @param size    количество записе на странице
     * @param from    номер страницы для пагинации
     */
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(name = "ids", required = false)
                                             List<Long> userIds,
                                     @RequestParam(name = "size", defaultValue = "10", required = false)
                                             @Positive Integer size,
                                     @RequestParam(name = "from", defaultValue = "0", required = false)
                                             @PositiveOrZero Integer from) {
        return userService.getAllUsers(userIds, from, size);
    }

    /**
     * Добавление пользователя
     *
     * @param userDto Обьект пользователь
     */
    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * Удаление пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
