package ru.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.category.service.CategoryService;
import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.category.model.Category;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto postCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(name = "from", defaultValue = "0",
            required = false) Integer from,
                                              @RequestParam(name = "size", defaultValue = "10",
                                                      required = false) Integer size) {
        return categoryService.getAllCategories(from, size);
    }
}
