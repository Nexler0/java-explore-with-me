package ru.explorewithme.util.category;

import ru.explorewithme.dto.category.CategoryDto;
import ru.explorewithme.model.category.Category;

/**
 * Преобразоватение Категории в Дто и обратно
 *
 * @see Category
 * @see CategoryDto
 */
public class CategoryMapper {
    /**
     * Преобразование в Дто
     *
     * @param category Обьект категории
     */
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    /**
     * Преобразование из Дто в обьект Категории
     *
     * @param category Обьект Дто категории
     */
    public static Category toCategory(CategoryDto category) {
        Category result = new Category();
        result.setId(category.getId());
        result.setName(category.getName());
        return result;
    }
}
