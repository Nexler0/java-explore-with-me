package ru.explorewithme.controller.client.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.comment.CommentDto;
import ru.explorewithme.service.comment.CommentService;

import java.util.List;

/**
 * Private API для работы с Комментариями
 *
 * @see CommentController
 */

@RestController
@RequestMapping("/users/{userId}/events{eventId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Добавление комментария пользователя к событию
     *
     * @param userId     идентификатор пользователя
     * @param eventId    идентифиикатор события
     * @param commentDto Дто комментария
     */
    @PostMapping
    private CommentDto postComment(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @RequestBody CommentDto commentDto) {
        return commentService.postComment(userId, eventId, commentDto);
    }

    /**
     * Получение списка коментариев
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     */
    @GetMapping
    private List<CommentDto> getComments(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        return commentService.getComments(userId, eventId);
    }

}
