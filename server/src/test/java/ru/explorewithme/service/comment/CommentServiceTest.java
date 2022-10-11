package ru.explorewithme.service.comment;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.explorewithme.dto.category.CategoryDto;
import ru.explorewithme.dto.comment.CommentDto;
import ru.explorewithme.dto.event.EventDtoIn;
import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.exception.ValidationException;
import ru.explorewithme.model.category.Category;
import ru.explorewithme.model.comment.Comment;
import ru.explorewithme.model.location.Location;
import ru.explorewithme.model.user.User;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.service.category.CategoryService;
import ru.explorewithme.service.event.EventService;
import ru.explorewithme.service.user.UserService;
import ru.explorewithme.util.comment.CommentMapper;
import ru.explorewithme.util.user.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TEST Сервиса Комментариев
 */

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceTest {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EntityManager em;
    private final UserService userService;
    private final EventService eventService;
    private final CommentService commentService;
    private final EventRepository eventRepository;
    private final CategoryService categoryService;

    private User user;
    private User user2;
    private EventDtoIn event;
    private Comment comment;

    @BeforeEach
    @Transactional
    void setUp() {

        user = new User(1L, "Jef", "user@123.ru");
        userService.addUser(UserMapper.toDto(user));
        user2 = new User(2L, "Jass", "Jass@mail.ru");
        userService.addUser(UserMapper.toDto(user2));
        CategoryDto category = categoryService.saveCategory(new Category(1L, "Экстрим"));

        event = EventDtoIn.builder()
                .id(1L)
                .category(category.getId())
                .annotation("Полет на параплане")
                .description("Приглашаем всех бесстрашных на экстримальный полет на параплане, есть ограничения.")
                .eventDate(LocalDateTime.now().plusDays(1).format(DATA_TIME_FORMATTER))
                .location(new Location(55.741487, 37.188709))
                .paid(true)
                .participantLimit(10L)
                .requestModeration(true)
                .title("Экстримальный полет")
                .build();

        eventService.postEventByUser(user.getId(), event);

    }

    @Transactional
    @Test
    void addNewCommentTest() {

        eventService.setPublishEvent(event.getId());
        RequestDto requestDto = userService.postUserRequest(user2.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto.getId());

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);

        commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment));
        TypedQuery<Comment> query = em.createQuery("select i from Comment i where i.id = :id", Comment.class);
        Comment check = query.setParameter("id", comment.getId()).getSingleResult();
        assertThat(check, equalTo(comment));
    }

    @Transactional
    @Test
    void addNewCommentWithoutApproveTest() {

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);

        Throwable throwable = assertThrows(ValidationException.class,
                () -> commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment)));
        assertThat(throwable.getMessage(), is("Ошибка сервиса коментариев"));
    }

    @Transactional
    @Test
    void getCommentsFromEvent() {

        User user3 = new User(3L, "Mesento", "Mesento@mail.ru");
        userService.addUser(UserMapper.toDto(user3));
        User user4 = new User(4L, "Fudjo", "Fudjo@mail.ru");
        userService.addUser(UserMapper.toDto(user4));

        eventService.setPublishEvent(event.getId());
        RequestDto requestDto = userService.postUserRequest(user2.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto.getId());

        RequestDto requestDto2 = userService.postUserRequest(user3.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto2.getId());

        RequestDto requestDto3 = userService.postUserRequest(user4.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto3.getId());

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);
        commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment));
        commentService.setApproveToComment(comment.getId());

        Comment comment2 = new Comment();
        comment2.setAuthor(user3);
        comment2.setEvent(eventRepository.findById(event.getId()).get());
        // Некорректный комментарий, в базе останется, но не будет показан пользователям
        comment2.setText("Было страшно бл..ть");
        comment2.setCreated(LocalDateTime.now().withNano(0));
        comment2.setId(2L);
        commentService.postComment(user3.getId(), event.getId(), CommentMapper.toCommentDto(comment2));

        Comment comment3 = new Comment();
        comment3.setAuthor(user4);
        comment3.setEvent(eventRepository.findById(event.getId()).get());
        comment3.setText("Очень высоко");
        comment3.setCreated(LocalDateTime.now().withNano(0));
        comment3.setId(3L);
        commentService.postComment(user4.getId(), event.getId(), CommentMapper.toCommentDto(comment3));
        commentService.setApproveToComment(comment3.getId());

        List<CommentDto> comments = commentService.getComments(user.getId(), event.getId());

        assertThat(comments, equalTo(List.of(CommentMapper.toCommentDto(comment),
                CommentMapper.toCommentDto(comment3))));
    }

    @Transactional
    @Test
    void deleteCommentTest() {

        User user3 = new User(3L, "Mesento", "Mesento@mail.ru");
        userService.addUser(UserMapper.toDto(user3));
        eventService.setPublishEvent(event.getId());
        RequestDto requestDto = userService.postUserRequest(user2.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto.getId());

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);
        commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment));

        commentService.deleteComment(user2.getId(), event.getId(), comment.getId());

        List<CommentDto> comments = commentService.getComments(user.getId(), event.getId());
        assertThat(comments, is(List.of()));
    }

    @Transactional
    @Test
    void deleteCommentNoOwnerTest() {

        User user3 = new User(3L, "Mesento", "Mesento@mail.ru");
        userService.addUser(UserMapper.toDto(user3));
        eventService.setPublishEvent(event.getId());
        RequestDto requestDto = userService.postUserRequest(user2.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto.getId());

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);
        commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment));

        Throwable throwable = assertThrows(ValidationException.class,
                () -> commentService.deleteComment(user3.getId(), event.getId(), comment.getId()));
        assertThat(throwable.getMessage(), is("Ошибка удаления, пользователь не является автором комментария"));
    }

    @Transactional
    @Test
    void deleteCommentByAdminTest() {

        User user3 = new User(3L, "Mesento", "Mesento@mail.ru");
        userService.addUser(UserMapper.toDto(user3));
        eventService.setPublishEvent(event.getId());
        RequestDto requestDto = userService.postUserRequest(user2.getId(), event.getId());
        eventService.confirmUserRequest(user.getId(), event.getId(), requestDto.getId());

        comment = new Comment();
        comment.setAuthor(user2);
        comment.setEvent(eventRepository.findById(event.getId()).get());
        comment.setText("Отлично полетали");
        comment.setCreated(LocalDateTime.now().withNano(0));
        comment.setId(1L);
        commentService.postComment(user2.getId(), event.getId(), CommentMapper.toCommentDto(comment));

        commentService.deleteComment(comment.getId());
        List<CommentDto> comments = commentService.getComments(user.getId(), event.getId());
        assertThat(comments, is(List.of()));
    }

    @Transactional
    @Test
    void deleteNotExistCommentTest() {
        Throwable throwable = assertThrows(NullPointerException.class,
                () -> commentService.deleteComment(comment.getId()));
    }
}
