package ru.explorewithme.model.compilation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import ru.explorewithme.model.event.Event;

import javax.persistence.*;
import java.util.List;

/**
 * Обьект Подборки
 *
 * @see ru.explorewithme.dto.compilation.CompilationDto
 * @see ru.explorewithme.service.compilation.CompilationService
 */

@Entity
@Table(name = "compilations")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Validated
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean pinned;

    private String title;

    @OneToMany
    @ToString.Exclude
    private List<Event> events;
}
