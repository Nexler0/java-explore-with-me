package ru.explorewithme.model.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.model.event.Event;
import ru.explorewithme.model.event.State;
import ru.explorewithme.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Обьект запроса
 *
 * @see ru.explorewithme.dto.request.RequestDto
 * @see ru.explorewithme.dto.request.RequestDtoIn
 * @see ru.explorewithme.service.request.RequestService
 */

@Entity
@Table(name = "requests")
@Validated
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Request {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    private State status = State.PENDING;
}
