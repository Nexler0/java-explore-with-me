package ru.explorewithme.model.comment;

import ru.explorewithme.model.event.Event;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * Обьект Комментария
 *
 * @see ru.explorewithme.dto.comment.CommentDto
 * @see ru.explorewithme.service.comment.CommentService
 */

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Validated
@EqualsAndHashCode
public class Comment {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    private LocalDateTime created;

    @EqualsAndHashCode.Exclude
    private Boolean approve = false;
}
