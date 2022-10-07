package ru.explorewithme.comment.model;

import ru.explorewithme.event.model.Event;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
}
