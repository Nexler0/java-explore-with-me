package ru.explorewithme.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Обьект Пользователя
 *
 * @see ru.explorewithme.model.user.User
 */

@Data
@AllArgsConstructor
public class UserShortDto {

    private Long id;
    private String name;
}
