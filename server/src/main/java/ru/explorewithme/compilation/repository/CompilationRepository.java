package ru.explorewithme.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.compilation.model.Compilation;

@RepositoryRestResource(path = "compilations")
public interface CompilationRepository extends JpaRepository<Compilation, Long>, CompilationRepositoryCustom {

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
