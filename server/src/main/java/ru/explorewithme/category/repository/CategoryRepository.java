package ru.explorewithme.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.category.model.Category;

import javax.validation.constraints.NotNull;


@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    @Query("select c from Category c order by c.id")
    Page<Category> findAll(@NotNull Pageable pageable);
}
