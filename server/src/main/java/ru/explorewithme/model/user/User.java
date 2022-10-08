package ru.explorewithme.model.user;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * Обьект Пользователь
 *
 * @see ru.explorewithme.dto.user.UserDto
 * @see ru.explorewithme.dto.user.UserShortDto
 * @see ru.explorewithme.service.user.UserService
 */

@Entity
@Table(name = "users")
@Validated
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users_name")
    private String name;

    @Column(name = "users_email")
    private String email;
}
