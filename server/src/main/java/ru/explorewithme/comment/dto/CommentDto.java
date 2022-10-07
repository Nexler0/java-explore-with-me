package ru.explorewithme.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

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
