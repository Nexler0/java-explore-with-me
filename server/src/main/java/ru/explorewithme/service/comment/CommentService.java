package ru.explorewithme.service.comment;

import ru.explorewithme.dto.comment.CommentDto;

import java.util.List;

/**
 * Сервис для работы с Комментариями
 *
 * @see ru.explorewithme.model.comment.Comment
 */

public interface CommentService {
    /**
     * Добавление комментария пользователя к событию
     *
     * @param userId     идентификатор пользователя
     * @param eventId    идентифиикатор события
     * @param commentDto Дто комментария
     */
    CommentDto postComment(Long userId, Long eventId, CommentDto commentDto);

    /**
     * Получение списка коментариев
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    List<CommentDto> getComments(Long userId, Long eventId);

    /**
     * Изменение статуса комментария на разрешенный
     *
     * @param comId идентификатор комментария
     */
    CommentDto setApproveToComment(Long comId);

}

