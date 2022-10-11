package ru.explorewithme.util.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.explorewithme.dto.comment.CommentDto;
import ru.explorewithme.model.comment.Comment;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Преобразование обьекта Комментария в Дто и обратно
 *
 * @see ru.explorewithme.model.comment.Comment
 * @see ru.explorewithme.dto.comment.CommentDto
 */

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Преобразование в обьект Комментарий
     *
     * @param commentDto      Дто коментария
     * @param userRepository  инжеция репозитория
     * @param eventRepository инжеция репозитория
     */
    public static Comment toComment(CommentDto commentDto,
                                    UserRepository userRepository,
                                    EventRepository eventRepository) {
        Comment comment = new Comment();
        comment.setAuthor(userRepository.findById(commentDto.getAuthorId()).get());
        comment.setEvent(eventRepository.findById(commentDto.getEventId()).get());
        comment.setText(commentDto.getText());
        comment.setCreated(LocalDateTime.now().withNano(0));
        return comment;
    }

    /**
     * Преобразование в Дто
     *
     * @param comment обьект Комментарий
     */
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getEvent().getId(),
                comment.getAuthor().getName(),
                comment.getAuthor().getId(),
                comment.getText(),
                comment.getCreated().format(DATA_TIME_FORMATTER));
    }
}
