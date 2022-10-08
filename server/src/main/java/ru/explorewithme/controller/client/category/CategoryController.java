package ru.explorewithme.controller.client.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.service.category.CategoryService;
import ru.explorewithme.dto.category.CategoryDto;

import java.util.List;

/**
 * Public API для пользователей, работа с категориями
 *
 * @see CategoryController
 */

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Получение списка категорий
     *
     * @param from номер страницы для пагинации
     * @param size количество записей на странице
     */
    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(name = "from", defaultValue = "0", required = false)
                                                      Integer from,
                                              @RequestParam(name = "size", defaultValue = "10", required = false)
                                                      Integer size) {
        return categoryService.getAllCategories(from, size);
    }

    /**
     * Получение категории по идетнификатору
     *
     * @param catId идентификатор категории
     */
    @GetMapping("/{catId}")
    public CategoryDto getAllCategories(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
