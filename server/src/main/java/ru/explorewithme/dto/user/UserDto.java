package ru.explorewithme.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Обьект Пользователя
 *
 * @see ru.explorewithme.model.user.User
 */

@Data
@RequiredArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
}
