package ru.explorewithme.dto.category;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Обьект категории
 *
 * @see ru.explorewithme.model.category.Category
 */

@Data
@RequiredArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
