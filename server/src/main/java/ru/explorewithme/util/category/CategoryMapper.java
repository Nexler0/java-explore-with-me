package ru.explorewithme.util.category;

import ru.explorewithme.dto.category.CategoryDto;
import ru.explorewithme.model.category.Category;

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
