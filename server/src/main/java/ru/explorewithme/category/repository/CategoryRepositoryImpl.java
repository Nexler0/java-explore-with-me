package ru.explorewithme.category.repository;

import org.springframework.context.annotation.Lazy;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final CategoryRepository categoryRepository;

    public CategoryRepositoryImpl(@Lazy CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
