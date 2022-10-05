package ru.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.category.service.CategoryService;
import ru.explorewithme.category.dto.CategoryDto;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(name = "from", defaultValue = "0", required = false)
                                                      Integer from,
                                              @RequestParam(name = "size", defaultValue = "10", required = false)
                                                      Integer size) {
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getAllCategories(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
