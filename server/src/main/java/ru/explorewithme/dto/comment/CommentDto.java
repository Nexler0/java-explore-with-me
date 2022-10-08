package ru.explorewithme.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Обьект комментария
 *
 * @see ru.explorewithme.model.comment.Comment
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentDto {

    @EqualsAndHashCode.Exclude
    private Long id;
    private Long itemId;
    private String authorName;
    private Long authorId;
    private String text;
    private LocalDateTime created;
}
