package ru.explorewithme.controller.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.service.category.CategoryService;
import ru.explorewithme.dto.category.CategoryDto;
import ru.explorewithme.model.category.Category;

import java.util.List;

/**
 * API для администратора при работе с категориями
 *
 * @see CategoryAdminController
 */

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    /**
     * Сохранение категории
     *
     * @param category обьект категория
     */

    @PostMapping
    public CategoryDto postCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    /**
     * Удаление категории
     *
     * @param catId id категории
     */
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }

    /**
     * Обновление категории
     *
     * @param category обьект категория
     */
    @PatchMapping
    public CategoryDto updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    /**
     * Получение списка всех сохраненных категорий
     *
     * @param from номер страницы для пагинации
     * @param size размер страницы для пагинации
     */
    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(name = "from", defaultValue = "0", required = false)
                                                      Integer from,
                                              @RequestParam(name = "size", defaultValue = "10",
                                                      required = false) Integer size) {
        return categoryService.getAllCategories(from, size);
    }
}
