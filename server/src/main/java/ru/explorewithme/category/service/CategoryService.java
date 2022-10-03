package ru.explorewithme.category.service;

import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.category.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto saveCategory(Category category);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Category category);

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);
}
