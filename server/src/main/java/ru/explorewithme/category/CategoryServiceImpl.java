package ru.explorewithme.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.explorewithme.category.model.Category;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        log.info("Category save: " + category);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        log.info("Update save: " + category);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        log.info("Category delete by id: " + catId);
        categoryRepository.deleteById(catId);
    }
}
