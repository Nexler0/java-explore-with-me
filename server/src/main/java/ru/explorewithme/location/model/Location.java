package ru.explorewithme.location.model;

import lombok.*;
import org.jetbrains.annotations.Range;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Range(from = -90, to = 90)
    private Double lat; // широта

    @NotNull
    @Range(from = -180, to = 180)
    private Double lon; // долгота

}
