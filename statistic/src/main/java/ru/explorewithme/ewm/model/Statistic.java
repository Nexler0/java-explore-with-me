package ru.explorewithme.ewm.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDateTime;

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
