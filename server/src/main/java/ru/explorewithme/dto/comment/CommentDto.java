package ru.explorewithme.dto.comment;

import lombok.*;

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
    private Long eventId;
    private String authorName;
    private Long authorId;
    private String text;
    private String created;
}
