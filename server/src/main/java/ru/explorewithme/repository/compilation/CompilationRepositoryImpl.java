package ru.explorewithme.repository.compilation;

import org.springframework.context.annotation.Lazy;

public class CompilationRepositoryImpl implements CompilationRepositoryCustom {

    private final CompilationRepository compilationRepository;

    public CompilationRepositoryImpl(@Lazy CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }
}
