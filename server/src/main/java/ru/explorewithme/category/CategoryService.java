package ru.explorewithme.category;

import ru.explorewithme.category.model.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    void deleteCategory(Long catId);

    Category updateCategory(Category category);
}
