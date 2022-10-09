package ru.explorewithme.model.statistic;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Обьект Статистики
 *
 * @see ru.explorewithme.dto.statistic.StatisticDto
 * @see ru.explorewithme.dto.statistic.StatisticDto
 * @see ru.explorewithme.dto.statistic.StatisticDtoShort
 * @see ru.explorewithme.service.statistic.StatisticService
 */

@Entity
@Table(name = "statistics")
@Validated
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Statistic {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
    @Transient
    private int hits;
}
