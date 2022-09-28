package ru.explorewithme.comment.model;

import ru.explorewithme.event.model.Event;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.user.model.User;

import javax.persistence.*;
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
    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Event item;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;
}
