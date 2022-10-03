package ru.explorewithme.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.category.dto.CategoryMapper;
import ru.explorewithme.category.model.Category;
import ru.explorewithme.category.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto saveCategory(Category category) {
        Category saveCategory = categoryRepository.save(category);
        log.info("Category save: " + saveCategory);
        return CategoryMapper.toCategoryDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(Category category) {
        Category saveCategory = categoryRepository.save(category);
        log.info("Update save: " + saveCategory);
        return CategoryMapper.toCategoryDto(saveCategory);
    }

    @Override
    public void deleteCategory(Long catId) {
        log.info("Category delete by id: " + catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        List<Category> result = categoryRepository.findAll(PageRequest.of(from, size)).toList();
        log.info("Get Category list (from: " + from + ", size: " + size + "): " + result);
        return result.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId).get());
    }
}
