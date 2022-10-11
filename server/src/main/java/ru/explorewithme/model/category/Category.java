package ru.explorewithme.model.category;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Обьект Категории
 *
 * @see ru.explorewithme.dto.category.CategoryDto
 * @see ru.explorewithme.service.category.CategoryService
 */

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(name = "name")
    private String name;

}
