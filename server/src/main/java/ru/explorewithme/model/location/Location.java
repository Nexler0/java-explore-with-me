package ru.explorewithme.model.location;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Обьект Локации
 *
 * @see ru.explorewithme.model.event.Event
 * @see ru.explorewithme.service.location.LocationService
 */

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@Validated
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double lat; // широта

    @NotNull
    private Double lon; // долгота

}
