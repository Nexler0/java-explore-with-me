package ru.explorewithme.controller.admin.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.comment.CommentDto;
import ru.explorewithme.service.comment.CommentService;

/**
 * API для работы с Комментариями для администратора
 *
 * @see ru.explorewithme.controller.client.comment.CommentController
 */

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentService commentService;

    /**
     * Изменение статуса комментария на разрешенный
     *
     * @param comId идентификатор комментария
     */
    @PostMapping("/{comId}/approve")
    public CommentDto setApproveToComment(@PathVariable Long comId) {
        return commentService.setApproveToComment(comId);
    }

}
