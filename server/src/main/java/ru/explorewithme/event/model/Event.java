package ru.explorewithme.event.model;

import ru.explorewithme.category.model.Category;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.location.model.Location;
import ru.explorewithme.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Validated
@EqualsAndHashCode
public class Event {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    private String annotation;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    private Long confirmedRequest = 0L;
    private LocalDateTime createdOn;
    private String description;
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @NonNull
    private Boolean paid;
    private Long participantLimit = 0L;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @NotNull
    @NotEmpty
    private String title;
    private int views;

}
