package ru.explorewithme.repository.compilation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.compilation.Compilation;

/**
 * Jpa Repository для подборок
 */


@RepositoryRestResource(path = "compilations")
public interface CompilationRepository extends JpaRepository<Compilation, Long>, CompilationRepositoryCustom {
    /**
     * Полчучение списка Подборок
     *
     * @param pinned   статус закрепелния на главной странице
     * @param pageable погинация
     */
    @Query("select c from Compilation c where c.pinned = ?1")
    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
