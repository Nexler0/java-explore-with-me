package ru.explorewithme.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.event.Event;
import ru.explorewithme.model.event.State;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Jpa Repository для событий
 */

@RepositoryRestResource(path = "events")
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    /**
     * Получение списка событий по идентифиактору с пагинацией
     *
     * @param id       идентификатор события
     * @param pageable пагитнцаия
     */
    @Query("select e from Event e where e.id = ?1")
    List<Event> findAllById(Long id, Pageable pageable);

    /**
     * Полчуние списка событий по статусу, времени начала и конца с погинацией
     *
     * @param state      статус события
     * @param rangeStart период начала поиска
     * @param rangeEnd   период окончания поиска
     * @param pageable   пагинация
     */
    @Query("select e from Event e where e.state = ?1 and e.createdOn between ?2 and ?3")
    Page<Event> findAllByStateAndCreatedOnIsBetween(State state,
                                                    LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd,
                                                    Pageable pageable);

    /**
     * Полчуение события по идентификатору создателя собития
     *
     * @param userId идентификатор пользоватлея
     */
    @Query("select e from Event e where e.initiator.id = ?1")
    Event findByInitiatorId(Long userId);

    /**
     * Полчуение списка событий по идентификатору создателя собития с  пагинацией
     *
     * @param userId   идентификатор пользоватлея
     * @param pageable пагинация
     */
    @Query("select e from Event e where e.initiator.id = ?1")
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);
}
