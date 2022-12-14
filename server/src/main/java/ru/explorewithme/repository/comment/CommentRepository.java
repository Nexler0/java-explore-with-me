package ru.explorewithme.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.comment.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Jpa Repository для комментариев
 */

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    /**
     * Получение списка комментариев по идентификатору события
     *
     * @param eventId идентификатор события
     */
    @Query("select c from Comment c where c.event.id = ?1 and c.approve = ?2")
    List<Comment> getAllByEventIdAndApprove(Long eventId, Boolean approve);

    /**
     * Проверка создан комментарий по идентификатору события
     *
     * @param eventId идентификатор события
     */
    @Query("select (count(c) > 0) from Comment c where c.event.id = ?1")
    Boolean existsCommentByEventId(Long eventId);

    /**
     * Проверка создан ли комментарий по идентификатору
     *
     * @param comId идентификатор комментария
     */
    @Query("select (count(c) > 0) from Comment c where c.id = ?1")
    boolean existsById(@NotNull Long comId);
}
