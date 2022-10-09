package ru.explorewithme.util.user;

import ru.explorewithme.dto.user.UserDto;
import ru.explorewithme.dto.user.UserShortDto;
import ru.explorewithme.model.user.User;

/**
 * Преобразование обьекта Пользователь в Дто и обратно
 *
 * @see User
 * @see UserDto
 * @see UserShortDto
 */

public class UserMapper {
    /**
     * Преобразование обьекта Пользователь в Дто
     *
     * @param user Обьект пользователь
     */
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    /**
     * Преобразование Дто в обьект Пользователь
     *
     * @param userDto Дто пользователя
     */
    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    /**
     * Преобразование обьекта Пользователь в короткое Дто
     *
     * @param user обьект Пользователь
     */
    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
