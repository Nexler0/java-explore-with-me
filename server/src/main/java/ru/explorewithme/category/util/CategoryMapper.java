package ru.explorewithme.category.util;

import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.category.model.Category;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryDto category) {
        Category result = new Category();
        result.setId(category.getId());
        result.setName(category.getName());
        return result;
    }
}
