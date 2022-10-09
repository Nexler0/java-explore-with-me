package ru.explorewithme.model.location;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Обьект Локации
 *
 * @see ru.explorewithme.model.event.Event
 */

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double lat; // широта

    @NotNull
    private Double lon; // долгота

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
