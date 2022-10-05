package ru.explorewithme.request.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.model.State;
import ru.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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
