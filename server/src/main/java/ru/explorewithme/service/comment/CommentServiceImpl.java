package ru.explorewithme.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.explorewithme.dto.comment.CommentDto;
import ru.explorewithme.exception.NotFoundException;
import ru.explorewithme.exception.ValidationException;
import ru.explorewithme.model.comment.Comment;
import ru.explorewithme.model.event.Event;
import ru.explorewithme.model.event.State;
import ru.explorewithme.model.request.Request;
import ru.explorewithme.model.user.User;
import ru.explorewithme.repository.comment.CommentRepository;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.repository.request.RequestRepository;
import ru.explorewithme.repository.user.UserRepository;
import ru.explorewithme.util.comment.CommentMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Override
    public CommentDto postComment(Long userId, Long eventId, CommentDto commentDto) {
        Event event = eventRepository.findById(eventId).get();
        Request request = requestRepository.findByRequesterId(userId);
        Comment comment = CommentMapper.toComment(commentDto, userRepository, eventRepository);
        if (event.getState().equals(State.PUBLISHED)
                && request.getStatus().equals(State.CONFIRMED)
                && event.getEventDate().isAfter(LocalDateTime.now().withNano(0))) {
            comment = commentRepository.save(comment);
            log.info("Comments postComment: {}", comment);
            return CommentMapper.toCommentDto(comment);
        } else {
            throw new ValidationException("Ошибка сервиса коментариев");
        }
    }

    @Override
    public List<CommentDto> getComments(Long userId, Long eventId) {
        List<Comment> result = new ArrayList<>();
        if (commentRepository.existsCommentByEventId(eventId)) {
            result = commentRepository.getAllByEventIdAndApprove(eventId, true);
        }
        log.info("Comments getComments: {}", result);
        return result.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto setApproveToComment(Long comId) {
        Comment comment = commentRepository.findById(comId).get();
        comment.setApprove(true);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        User author = userRepository.findById(userId).get();
        Comment comment = commentRepository.findById(commentId).get();
        if (eventRepository.existsById(commentId)) {
            if (comment.getAuthor().equals(author)) {
                commentRepository.delete(comment);
            } else {
                throw new ValidationException("Ошибка удаления, пользователь не является автором комментария");
            }
        } else {
            throw new NotFoundException("Данный коментарий не найден");
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        commentRepository.delete(comment);
    }
}
