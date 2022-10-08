package ru.explorewithme.service.category;

import ru.explorewithme.dto.category.CategoryDto;
import ru.explorewithme.model.category.Category;

import java.util.List;

/**
 * Сервис для работы с Категориями
 *
 * @see Category
 */

public interface CategoryService {
    /**
     * Сохранение категории
     *
     * @param category Обьект категория
     * @see Category
     */
    CategoryDto saveCategory(Category category);

    /**
     * Удаление категории по идентификатору
     *
     * @param catId идентификатор категории
     */
    void deleteCategory(Long catId);

    /**
     * Обновление категории
     *
     * @param category Обьект категория
     * @see Category
     */
    CategoryDto updateCategory(Category category);

    /**
     * Полчуние списка категоий с пагинацией
     *
     * @param from номер страницы для пагинации
     * @param size количество записей на странице
     */
    List<CategoryDto> getAllCategories(int from, int size);

    /**
     * Получение категории по идентификатору
     *
     * @param catId идентификатор категории
     */
    CategoryDto getCategoryById(Long catId);
}
